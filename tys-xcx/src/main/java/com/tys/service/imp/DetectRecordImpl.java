package com.tys.service.imp;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tys.entity.vo.DetectRecord;
import com.tys.entity.vo.DetectRecordVo;
import com.tys.entity.vo.DetectScoreVo;
import com.tys.entity.vo.Photo;
import com.tys.service.DetectRecordService;
import com.tys.service.imp.dao.*;
import com.tys.util.tool.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author haoxu
 * @Date 2019/3/14 13:38
 **/
@Service
@Transactional
public class DetectRecordImpl implements DetectRecordService {

    @Autowired
    private DetectRecordMapper detectRecordMapper;

    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Autowired
    private DrComplexSettingMapper drComplexSettingMapper;

    @Autowired
    private DrProblemSettingMapper drProblemSettingMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private FaceValueMapper faceValueMapper;

    private Logger logger = LoggerFactory.getLogger(DetectRecordService.class);

    @Override
    @Transactional(readOnly = true)
    public List<DetectRecord> selectByMemberId(Integer memberId) {
        try {
            return detectRecordMapper.selectByMemberId(memberId);
        } catch (Exception e) {
            logger.error("检测报告selectByMemberId错误，memberId:{},error:{}", memberId, e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Map selectTwoByMemberId(Integer memberId) {
        try {
            Map map = Maps.newHashMap();
            List<DetectRecord> detectRecords =  detectRecordMapper.selectTwoByMemberId(memberId);
            if(!ObjectUtils.isEmpty(detectRecords)){
                HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
                Map scoreSort = hashOps.entries("detectScoreSort");
                DetectRecord first = detectRecords.iterator().next();
                String result = first.getResult();
                if(result != null ){
                    Map res = JSON.parseObject(result);
                    if(res.get("qualified") != null && res.get("qualified").toString().equals("1")){
                        Object comprehensiveScore = res.get("comprehensiveScore");
                        if(comprehensiveScore != null){
                            comprehensiveScore = Integer.valueOf(comprehensiveScore.toString());
                            int count100 = Integer.valueOf(scoreSort.get("100").toString());
                            int count99 = Integer.valueOf(scoreSort.get("99").toString());
                            int count89 = Integer.valueOf(scoreSort.get("89").toString());
                            int count79 = Integer.valueOf(scoreSort.get("79").toString());
                            int count69 = Integer.valueOf(scoreSort.get("69").toString());
                            int count = count69 + count79 + count89 + count99 + count100;
                            int percentage = 60;
                            if((Integer)comprehensiveScore == 100){
                                percentage = 100;
                            }else if((Integer)comprehensiveScore <= 99 && (Integer)comprehensiveScore > 89){
                                percentage = (int) Math.ceil((count69 + count79 + count89 + count99)/count*100);
                            }else if((Integer)comprehensiveScore <= 89 && (Integer)comprehensiveScore > 79){
                                percentage = (int) Math.ceil((count69 + count79 + count89)/count*100);
                            }else if((Integer)comprehensiveScore <= 79 && (Integer)comprehensiveScore > 69){
                                percentage = (int) Math.ceil((count69 + count79)/count*100);
                            }else if((Integer)comprehensiveScore <= 69 ){
                                percentage = (int) Math.ceil(count69 /count*100);
                            }
                            if(percentage == 100 && (Integer)comprehensiveScore != 100){
                                percentage = 98;
                            }else if(percentage < 60){
                                percentage = 60;
                            }
                            map.put("percentage",percentage);
                        }
                    }
                }
            }
            map.put("detectRecords",detectRecords);
            return map;
        } catch (Exception e) {
            logger.error("selectTowByMemberId，memberId:{},error:{}", memberId, e.getMessage());
        }
        return null;
    }

    @Override
    public Map queryTimesByMemberId(Integer memberId) {
        Map map = Maps.newHashMap();
        try {
            map.put("detectionsNumber",memberInfoMapper.selectDetectionsNumber(memberId));
            map.put("pastSevenNumber",detectRecordMapper.selectTimes(memberId,DateUtil.getPastDate(7)));
        }catch (Exception e){
            logger.error("queryTimesByMemberId failed,memberId:{},error:{}",memberId,e.getMessage());
        }
        return map;
    }

    @Override
    public DetectRecord selectValidLastTime(Integer mid) {
        try {
            return detectRecordMapper.selectValidLastTime(mid);
        } catch (Exception e) {
            logger.error("检测报告selectValidLastTime错误，memberId:{},error:{}", mid, e.getMessage());
        }
        return null;
    }

    @Override
    public DetectRecordVo queryDrSettingData(Integer id) {
        DetectRecord record = detectRecordMapper.selectByPrimaryKey(id);
        DetectRecordVo detectRecordVo = new DetectRecordVo();
        if (record.getResult() == null) {
            logger.warn("检测报告计算结果为空，无法获得对应设置数据，id:{}", id);
        } else {
            try {
                DetectScoreVo scoreVo = JSON.parseObject(record.getData(), DetectScoreVo.class);
                Map result = JSON.parseObject(record.getResult());
                Byte sex = Byte.valueOf(result.get("sex").toString());
                detectRecordVo.setFaceValues(faceValueMapper.selectFaceValueBySorce(sex,scoreVo));
                Byte skinType = null;
                if(result.get("skinType") != null){
                    skinType = Byte.valueOf(result.get("skinType").toString());
                }else {
                    skinType = 3;
                }
                List<Byte> skinTypes = Lists.newLinkedList();
                skinTypes.add(skinType);
                if(result.get("skinAge") != null){
                    Integer skinAge = Integer.valueOf(result.get("skinAge").toString());
                    if(skinAge < 18){
                        skinTypes.add((byte)5);
                    }else if(skinAge >=18 && skinAge <= 25){
                        skinTypes.add((byte)6);
                    }else if(skinAge > 25 && skinAge <= 35){
                        skinTypes.add((byte)7);
                    }else if(skinAge > 35 && skinAge <= 45){
                        skinTypes.add((byte)8);
                    }else{
                        skinTypes.add((byte)9);
                    }
                }
                detectRecordVo.setDrComplexSettings(drComplexSettingMapper.selectDrComplexBySkinType(skinTypes));
                detectRecordVo.setDrProblemSettings(drProblemSettingMapper.selectDrProblemByScore(skinType,scoreVo));
                return detectRecordVo;
            }catch (Exception e){
                logger.error("获取文案数据错误，did:{},error:{}",id,e.getMessage());
            }
        }
        return null;
    }

    public String selectThreePath(Integer mid){
        return detectRecordMapper.selectThreePathByMid(mid);
    }

}
