package com.tys.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.tys.constant.Constant;
import com.tys.entity.vo.*;
import com.tys.service.DetectRecordService;
import com.tys.service.imp.dao.*;
import com.tys.util.tool.DateUtil;
import com.tys.util.vo.ReturnMessage;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.*;

/**
 * @Author haoxu
 * @Date 2019/3/14 9:31
 **/
@Service
public class DetectRecordServiceImpl implements DetectRecordService {

    private Logger logger = LoggerFactory.getLogger(DetectRecordService.class);

    @Autowired
    private DetectRecordMapper detectRecordMapper;

    @Autowired
    private EquipMentMapper equipMentMapper;

    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Autowired
    private DrComplexSettingMapper drComplexSettingMapper;

    @Autowired
    private DrProblemSettingMapper drProblemSettingMapper;

    @Autowired
    private FaceValueMapper faceValueMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


//    @Transactional()
    @Override
    public ReturnMessage uploadDetectRecord(PhotoMsg photoMsg) {
        if (photoMsg.getEmId() == null) {
            logger.warn("uploadDetectRecord失败，emId 不能为空");
            return new ReturnMessage("need property emId");
        } else if (photoMsg.getUserId() == null) {
            logger.warn("uploadDetectRecord失败， userId 不能为空");
            return new ReturnMessage("need property userId");
        } else if (photoMsg.getPhotos() == null) {
            logger.warn("uploadDetectRecord失败， photos 不能为空");
            return new ReturnMessage("need property photos");
        } else if (photoMsg.getUploadType() == null) {
            logger.warn("uploadDetectRecord失败，uploadType 不能为空");
            return new ReturnMessage("need property uploadType");
        }
        Timestamp lasttime = new Timestamp(Calendar.getInstance().getTimeInMillis());
        Integer equipMentId = photoMsg.getEmId();
        Integer memberInfoId = photoMsg.getUserId();
        MemberInfo memberInfo = memberInfoMapper.selectByPrimaryKey(memberInfoId);
        if (memberInfo == null) {
            logger.warn("uploadDetectRecord失败，equipMentId:{},memberInfoId:{},memberinfo not exits",equipMentId,memberInfoId);
            return new ReturnMessage("memberinfo not exits");
        }
        EquipMent equipMent = equipMentMapper.selectByPrimaryKey(equipMentId);
        if (equipMent == null) {
            logger.warn("uploadDetectRecord失败，equipMentId:{},memberInfoId:{},equipment not exits",equipMentId,memberInfoId);
            return new ReturnMessage("equipment not exits");
        }
        equipMentMapper.updateLasttime(equipMentId, lasttime);
        memberInfo.setDetectionsLasttime(lasttime);
        if(photoMsg.getUploadType().equals((byte)0)){
            memberInfo.setSilentNumber( (memberInfo.getSilentNumber()==null?0:memberInfo.getSilentNumber()) +1 );
        }else {
            memberInfo.setDetectionsNumber( (memberInfo.getDetectionsNumber()==null?0:memberInfo.getDetectionsNumber())+1 );
        }
        memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
        DetectRecord record = new DetectRecord();
        record.setEquipmentId(equipMentId);
        record.setMemberId(memberInfoId);
        record.setDetectTime(lasttime);
        record.setDetectLocation(equipMent.getPlaceAddress());
        record.setDetectProvince(equipMent.getEmProvince());
        record.setDetectCity(equipMent.getEmCity());
        record.setUploadType(photoMsg.getUploadType());
        record.setPhotoPath(JSON.toJSONString(photoMsg.getPhotos()));
        detectRecordMapper.insertSelective(record);
        Integer id = record.getId();
        String photoMsgStr = JSON.toJSONString(photoMsg);
        logger.info("图片上传成功,rid:{},photoMsg:{}",id,photoMsgStr);
        JSONObject params = new JSONObject();
        params.put("rid", id);
        params.put("photos", photoMsg.getPhotos());
        //发送图片信息到AI
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        try {
            HttpEntity<String> entity = new HttpEntity<String>(params.toString(), headers);
            ResponseEntity<String> responseString = restTemplate.postForEntity(Constant.AI_URL, entity,String.class);
            if (responseString.getStatusCode() != HttpStatus.OK) {
                logger.warn("发送ai数据错误，rid:{},error:{}",id,responseString);
                SetOperations<String, Integer> setOps =  redisTemplate.opsForSet();
                String date = DateFormat.getDateInstance().format(new Date());
                setOps.add(Constant.PUSH_AI_FAILED+date,id);
            }
            logger.info("发送图片到ai成功,rid:{},photoMsg:{}",id,photoMsgStr);
        }catch (Exception e){
            logger.error("发送ai数据错误，rid:{},error:{}",id,e.getMessage());
            SetOperations<String, Integer> setOps =  redisTemplate.opsForSet();
            String date = DateFormat.getDateInstance().format(new Date());
            setOps.add(Constant.PUSH_AI_FAILED+date,id);
        }
        return new ReturnMessage("success", null);
    }


    @Transactional
    @Override
    public ReturnMessage computeDetectRecord(DetectScoreVo detectScoreVo) {
        Integer rid = detectScoreVo.getRid();
        if (rid == null) {
            logger.warn("computeDetectRecord失败，rid不存在");
            return new ReturnMessage("need property rid");
        }
        DetectRecord record = detectRecordMapper.selectByPrimaryKey(rid);
        if (record == null) {
            logger.warn("computeDetectRecord失败，rid:{},detectrecord not exits",rid);
            return new ReturnMessage("detectrecord not exits");
        }
        DetectRecord preRecord = detectRecordMapper.selectPreRecord(record.getMemberId(), record.getDetectTime());
        MemberInfo memberInfo = memberInfoMapper.selectByPrimaryKey(record.getMemberId());
        if (memberInfo == null) {
            logger.warn("computeDetectRecord失败，rid:{},memberinfo not exits",rid);
            return new ReturnMessage("memberinfo not exits");
        }
        Map result = Maps.newHashMap();
        List<Photo> photos = JSON.parseArray(record.getPhotoPath(),Photo.class);
        String dataStr = JSON.toJSONString(detectScoreVo);
        record.setData(dataStr);
        Short age = memberInfo.getAge();
        Byte sex = memberInfo.getSex();
        result.put("sex",sex);
        result.put("age",age);
        Map oiliness  = Maps.newHashMap(),acne  = Maps.newHashMap(),fleck  = Maps.newHashMap(),wrinkle  = Maps.newHashMap(),pore = Maps.newHashMap();
        photos.stream().forEach(photo -> {
            if(photo.getIsQualified().equals((byte)0)){
                result.put("qualified",0);
            }
            switch (photo.getName()){
                case "distant":
                    setPhotoInfo(photo,oiliness,detectScoreVo.getAxunge());
                    break;
                case "white":
                    setPhotoInfo(photo,wrinkle, detectScoreVo.getWrinkle());
                    break;
                case "cross":
                    setPhotoInfo(photo,acne, detectScoreVo.getAcne());
                    break;
                case "uv":
                    setPhotoInfo(photo,pore, detectScoreVo.getPore());
                    break;
                case "wushi":
                    setPhotoInfo(photo,fleck, detectScoreVo.getFleck());
                    break;
                default:
                    break;
            }
        });
        if(result.get("qualified") == null){
            result.put("qualified",1);
        }
        if ((int)oiliness.get("score") != -1) {
            Integer skinType = computeSkinType(age, sex, detectScoreVo.getAxunge());
            Integer skinAge = computeSkinAge(age,skinType,detectScoreVo.getAxunge());
            result.put("skinType", skinType);
            result.put("skinAge", skinAge);
        }else{
            result.put("skinType", null);
            result.put("skinAge", null);
        }
        result.put("oiliness",oiliness);
        result.put("acne",acne);
        result.put("fleck",fleck);
        result.put("wrinkle",wrinkle);
        result.put("pore",pore);
        int comprehensiveScore = (byte) Math.round((((Integer)oiliness.get("score") == -1 ? 0:(Integer)oiliness.get("score"))
                + ((Integer)wrinkle.get("score") == -1 ? 0:(Integer)wrinkle.get("score"))
                + ((Integer)acne.get("score") == -1 ? 0:(Integer)acne.get("score"))
                + ((Integer)pore.get("score") == -1 ? 0:(Integer)pore.get("score"))
                + ((Integer)fleck.get("score")== -1 ? 0:(Integer)fleck.get("score"))) / 5);
        countScoreSort(comprehensiveScore);
        result.put("comprehensiveScore", comprehensiveScore);
        if (preRecord != null) {
            result.put("compareWithPre", comprehensiveScore - (int) (JSON.parseObject(preRecord.getResult()).get("comprehensiveScore")));
        } else
            result.put("compareWithPre", null);
        record.setResult(JSONObject.toJSONString(result));
        detectRecordMapper.updateByPrimaryKeySelective(record);
        redisTemplate.delete(Constant.COSMETICS_CACHE_NAME+"::"+record.getMemberId());
        logger.info("肤质计算成功，rid:{},data:{}",rid,dataStr);
        return new ReturnMessage("success", null);
    }

    private void setPhotoInfo(Photo photo,Map map,Double score ){
        map.put("url",photo.getUrl());
        if(photo.getIsQualified().equals((byte)1)){
            if(photo.getName().equals("distant")){
                map.put("axunge",score);
                map.put("score",computeOiliness(score));
            }else {
                map.put("score",computeIndex(0.1, 0.4, score));
            }
        }else {
            map.put("score",-1);
        }
    }

    private int computeOiliness(double axunge){
        int oiliness = 0;
        if(axunge >0 && axunge < 0.1){
            oiliness = (int)(200 * axunge + 60);
        }else if(axunge >= 0.1 && axunge <= 0.25){
            oiliness = (int)(400/3*axunge+200/3);
        }else if(axunge > 0.25 && axunge <=0.4){
            oiliness = (int)(400/3-400/3*axunge);
        }else if(axunge > 0.4 && axunge < 1){
            oiliness = (int)(230/3-100/3*axunge);
        }
        return oiliness;
    }

    private Integer computeSkinAge(int age,int skinType,double axunge){
        Integer skinAge = age;
        switch (skinType) {
            case 1:
                skinAge += 3;
                break;
            case 2:
                skinAge -= 3;
                break;
            case 4:
                skinAge -= 1;
                break;
        }
        if (age <= 30) {
            if (axunge <= 0.01) {
                skinAge -= 3;
            } else if (axunge > 0.01 && axunge <= 0.5) {
            } else if (axunge > 0.05 && axunge <= 0.1) {
                skinAge++;
            } else if (axunge > 0.1 && axunge <= 0.2) {
                skinAge += 2;
            } else if (axunge > 0.2 && axunge <= 0.3) {
                skinAge += 4;
            } else if (axunge > 0.3 && axunge <= 0.4) {
                skinAge += 8;
            } else if (axunge > 0.4 && axunge <= 0.5) {
                skinAge += 16;
            } else if (axunge > 0.5 && axunge <= 0.6) {
                skinAge += 32;
            } else if (axunge > 0.6) {
                skinAge += 64;
            }
        } else if (age > 30 && age <= 40) {
            if (axunge <= 0.01) {
                skinAge -= 6;
            } else if (axunge > 0.01 && axunge <= 0.5) {
                skinAge -= 3;
            } else if (axunge > 0.05 && axunge <= 0.1) {
            } else if (axunge > 0.1 && axunge <= 0.2) {
                skinAge += 1;
            } else if (axunge > 0.2 && axunge <= 0.3) {
                skinAge += 2;
            } else if (axunge > 0.3 && axunge <= 0.4) {
                skinAge += 3;
            } else if (axunge > 0.4 && axunge <= 0.5) {
                skinAge += 4;
            } else if (axunge > 0.5 && axunge <= 0.6) {
                skinAge += 8;
            } else if (axunge > 0.6) {
                skinAge += 16;
            }
        } else if (age > 40 && age <= 60) {
            if (axunge <= 0.01) {
                skinAge -= 16;
            } else if (axunge > 0.01 && axunge <= 0.5) {
                skinAge -= 12;
            } else if (axunge > 0.05 && axunge <= 0.1) {
                skinAge -= 8;
            } else if (axunge > 0.1 && axunge <= 0.2) {
                skinAge -= 4;
            } else if (axunge > 0.2 && axunge <= 0.3) {
                skinAge -= 2;
            } else if (axunge > 0.3 && axunge <= 0.4) {
            } else if (axunge > 0.4 && axunge <= 0.5) {
                skinAge += 2;
            } else if (axunge > 0.5 && axunge <= 0.6) {
                skinAge += 4;
            } else if (axunge > 0.6) {
                skinAge += 6;
            }
        } else if (age > 60) {
            if (axunge <= 0.01) {
                skinAge -= 32;
            } else if (axunge > 0.01 && axunge <= 0.5) {
                skinAge -= 16;
            } else if (axunge > 0.05 && axunge <= 0.1) {
                skinAge -= 12;
            } else if (axunge > 0.1 && axunge <= 0.2) {
                skinAge -= 8;
            } else if (axunge > 0.2 && axunge <= 0.3) {
                skinAge -= 6;
            } else if (axunge > 0.3 && axunge <= 0.4) {
                skinAge -= 4;
            } else if (axunge > 0.4 && axunge <= 0.5) {
                skinAge -= 2;
            } else if (axunge > 0.5 && axunge <= 0.6) {
            } else if (axunge > 0.6) {
                skinAge += 1;
            }
        }
        return skinAge;
    }

    private void countScoreSort(int comprehensiveScore){
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        Map scoreSort = hashOps.entries("detectScoreSort");
        if(scoreSort != null){
            String key = null;
            if(comprehensiveScore == 100){
                key = "100";
            }else if(comprehensiveScore <= 99 && comprehensiveScore > 89){
                key = "99";
            }else if(comprehensiveScore <= 89 && comprehensiveScore > 79){
                key = "89";
            }else if(comprehensiveScore <= 79 && comprehensiveScore > 69){
                key = "79";
            }else if(comprehensiveScore <= 69 ){
                key = "69";
            }
            hashOps.put("detectScoreSort",key,Integer.valueOf(scoreSort.get(key).toString())+1+"");
        }
    }


    private Integer computeSkinType(Short age, Byte sex, Double axunge) {
        Integer skinType = null;
        if (axunge != null) {
            if (age < 12 && axunge < 0.1) {
                skinType = 3;//中性
            } else if (age > 12 && axunge < 0.1) {
                skinType = 1;//干性
            } else if (axunge >= 0.1 && axunge <= 0.4) {
                skinType = 4;//混合性
            } else {
                skinType = 2;//油性
            }
        }
        return skinType;
    }

    private int computeIndex(Double min, Double max, Double score) {
        int result = 0;
        if (score != null) {
            if (score <= min) {
                result = (int) Math.round(80 + ((min - score) / min) * 20);
            } else if (score > min && score <= max) {
                result = (int) Math.round(60 + ((max - score) / (max - min)) * 20);
            } else if (score > max) {
                result = (int) Math.round((1 - score) / (1 - max) * 60);
            }
        }
        return result;
    }


    @Override
    public PageInfo<DetectMemberVo> queryDetectMember(DetectMemberVo record) {
        PageInfo<DetectMemberVo> pageInfo = null;
        if (record.getCurrentPage() != null && record.getPageSize() != null) {
            pageInfo = PageHelper.startPage(record.getCurrentPage(), record.getPageSize()).doSelectPageInfo(() -> detectRecordMapper.queryDetectMember(record));
        } else
            pageInfo = new PageInfo<>(detectRecordMapper.queryDetectMember(record));
        return pageInfo;
    }

    public DetectRecordVo queryDrSettingData(Integer id) {
        DetectRecord record = detectRecordMapper.selectByPrimaryKey(id);
        DetectRecordVo detectRecordVo = new DetectRecordVo();
        DetectScoreVo scoreVo = JSON.parseObject(record.getData(), DetectScoreVo.class);
        Map result = JSON.parseObject(record.getResult());
        Byte sex = Byte.valueOf(result.get("sex").toString());
        Byte skinType = null;
        if(result.get("skinType") != null){
            skinType = Byte.valueOf(result.get("skinType").toString());
        }else {
            skinType = 3;
        }
        detectRecordVo.setFaceValues(faceValueMapper.selectFaceValueBySorce(sex, scoreVo));
        detectRecordVo.setDrComplexSettings(drComplexSettingMapper.selectDrComplexBySkinType(skinType));
        detectRecordVo.setDrProblemSettings(drProblemSettingMapper.selectDrProblemByScore(skinType, scoreVo));
        return detectRecordVo;
    }

    @Override
    public int updateThreeDate(Integer rid) {
        DetectRecord record = detectRecordMapper.selectByPrimaryKey(rid);
        if (record == null || record.getPhotoPath() == null) {
            logger.warn("3d更新失败,未查询到对应的detectrecord数据，rid:{}",rid);
            return 0;
        }
        JSONArray photos = JSON.parseArray(record.getPhotoPath());
        Optional<Object> optional = photos.stream().filter(photo -> Constant.THREED_KEY.equalsIgnoreCase(((Map) photo).get("name").toString())).findFirst();
        if (optional.isPresent()) {
            Map photo = (Map) optional.get();
            String threePath = photo.get("url").toString() + Constant.THTREED_SUFFIX;
            logger.info("3d更新成功，rid:{}，threePath:{}",rid,threePath);
            return detectRecordMapper.updateThreePathByPrimaryKey(rid, threePath);
        }else {
            logger.warn("3d更新失败，rid:{}",rid);
        }
        return 0;
    }

}
