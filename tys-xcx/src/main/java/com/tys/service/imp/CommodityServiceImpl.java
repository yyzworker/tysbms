package com.tys.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tys.entity.es.Commodity;
import com.tys.entity.vo.DetectRecord;
import com.tys.entity.vo.MemberInfo;
import com.tys.service.CommodityService;
import com.tys.service.imp.dao.DetectRecordMapper;
import com.tys.util.es.constant.AssociateType;
import com.tys.util.es.constant.QueryType;
import com.tys.util.es.server.ElasticsearchServer;
import com.tys.util.es.vo.Condition;
import com.tys.util.es.vo.Page;
import com.tys.util.es.vo.QueryInfo;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;


/**
 * @Author haoxu
 * @Date 2019/4/1 11:21
 **/
@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private ElasticsearchServer elasticsearchServer;

    @Autowired
    private DetectRecordMapper detectRecordMapper;

    private Logger logger = LoggerFactory.getLogger(CommodityService.class);

    @Override
    public Page<Commodity> search(String name, List<Byte> typeCode, List<Byte> suitableSkinCodes, Byte fitSensitive, Byte pregnantCautionCount, int pageNumber) {
        QueryInfo queryInfo = new QueryInfo(Commodity.class);
        queryInfo.setPageSize(10);
        queryInfo.setPageNum(pageNumber);
        List<Condition> conditions = Lists.newLinkedList();
        try {
            /*if(name != null){
                queryBuilder1.should(QueryBuilders.multiMatchQuery(name,"name","name.standard","alias","alias.standard"));
                queryBuilder1.should(QueryBuilders.nestedQuery("compositions",QueryBuilders.matchQuery("compositions.name",name), ScoreMode.Max));
                queryBuilder.must(queryBuilder1);
            }*/
            if(name != null){
                conditions.add(new Condition(new String[]{"name","name.standard","alias","alias.standard"}, QueryType.MULTI_MATCH,name));
            }
            if(!ObjectUtils.isEmpty(typeCode)){
                conditions.add(new Condition(new String[]{"typeCode"},QueryType.TREMS,typeCode.toArray(new Byte[typeCode.size()])));
            }
            if(!ObjectUtils.isEmpty(suitableSkinCodes)){
                conditions.add(new Condition(new String[]{"suitableSkinCodes"},QueryType.TREMS,suitableSkinCodes.toArray(new Byte[suitableSkinCodes.size()])));
            }
            if(fitSensitive != null){
                conditions.add(new Condition(new String[]{"fitSensitive"},QueryType.TREM,fitSensitive));
            }
            if(pregnantCautionCount != null){
                conditions.add(new Condition(new String[]{"pregnantCautionCount"},QueryType.TREM,pregnantCautionCount));
            }
            conditions.add(new Condition(new String[]{"status"},QueryType.TREM,1));
            queryInfo.setIncludes(new String[]{"id","imgUrl","name","enName","safeGrade","typeCode"});
            queryInfo.setConditions(conditions);
            return elasticsearchServer.search(queryInfo);
        }catch (Exception e){
            logger.error("search failed，name:{},pageNumber:{},error:{}",name,pageNumber,e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Page<Commodity> searchByCompName(String name, List<Byte> typeCode, List<Byte> suitableSkinCodes, Byte fitSensitive, Byte pregnantCautionCount, int pageNumber) {
        QueryInfo queryInfo = new QueryInfo(Commodity.class);
        queryInfo.setPageSize(10);
        queryInfo.setPageNum(pageNumber);
        List<Condition> conditions = Lists.newLinkedList();
        try {
            if(name != null){
                conditions.add(new Condition(new String[]{"compositions"}, QueryType.NESTED, AssociateType.MUST, new Condition(new String[]{"compositions.name"}, QueryType.MATCH_PHRASE, name), null));
            }
            if(!ObjectUtils.isEmpty(typeCode)){
                conditions.add(new Condition(new String[]{"typeCode"},QueryType.TREMS,typeCode.toArray(new Byte[typeCode.size()])));
            }
            if(!ObjectUtils.isEmpty(suitableSkinCodes)){
                conditions.add(new Condition(new String[]{"suitableSkinCodes"},QueryType.TREMS,suitableSkinCodes.toArray(new Byte[suitableSkinCodes.size()])));
            }
            if(fitSensitive != null){
                conditions.add(new Condition(new String[]{"fitSensitive"},QueryType.TREM,fitSensitive));
            }
            if(pregnantCautionCount != null){
                conditions.add(new Condition(new String[]{"pregnantCautionCount"},QueryType.TREM,pregnantCautionCount));
            }
            conditions.add(new Condition(new String[]{"status"},QueryType.TREM,1));
            queryInfo.setIncludes(new String[]{"id","imgUrl","name","enName","safeGrade","typeCode"});
            queryInfo.setConditions(conditions);
            return elasticsearchServer.search(queryInfo);
        }catch (Exception e){
            logger.error("searchByCompName failed，name:{},pageNumber:{},error:{}",name,pageNumber,e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Commodity queryById(Integer id) {
        try {
          return elasticsearchServer.getById(Commodity.class,id+"");
        }catch (Exception e){
            logger.error("商品搜索queryById错误，commodityId:{},error:{}",id,e.getMessage());
        }
        return null;
    }

    @Override
    public Map computeSuitability(MemberInfo memberInfo, DetectRecord detectRecord, List<Commodity> commodities) {
        if(memberInfo == null || detectRecord == null || detectRecord.getResult() == null || ObjectUtils.isEmpty(commodities)){
            if(memberInfo == null){
                logger.warn("memberInfo == null)，无法计算商品匹配度");
            }else if(detectRecord == null){
                logger.warn("detectRecord == null，无法计算商品匹配度,memberid:{}",memberInfo.getId());
            }else if(detectRecord.getResult() == null){
                logger.warn("result == null，无法计算商品匹配度,memberId:{},recordId:{}",memberInfo.getId(),detectRecord.getId());
            }else if(ObjectUtils.isEmpty(commodities)){
                logger.warn("commodities is empty，无法计算商品匹配度,memberId:{},recordId:{}",memberInfo.getId(),detectRecord.getId());
            }
            return null;
        }
        Map result = JSON.parseObject(detectRecord.getResult());

        //五项数据有任意一项为空不计算匹配度
        if(result.get("qualified").toString().equals("0") ){
            logger.warn("五项分析数据不全,无法计算商品匹配度,memberId:{},recordId:{}",memberInfo.getId(),detectRecord.getId());
           return null;
        }
        final Map map = Maps.newHashMap();
        Short age = result.get("age") != null ?Short.valueOf(result.get("age").toString()): null;
        Byte sex = result.get("sex") != null ?Byte.valueOf(result.get("sex").toString()): null;
        commodities.stream().forEach(commodity -> {
            int sensitiveScore = 0;
            Integer skinType = Integer.valueOf(result.get("skinType").toString());
            //基础分
            if(!ObjectUtils.isEmpty(commodity.getSuitableSkinCodes()) && commodity.getSuitableSkinCodes().indexOf(skinType) > -1 && (commodity.getSuitableSexCode() == 0 || commodity.getSuitableSexCode() == (int)sex) &&
                    (commodity.getAcneCount() == 0 || (skinType != 2 && skinType != 4))){
                sensitiveScore += 50;
            }
            //功效分
            Integer colorSpotIndex = getScore(result,"fleck");//色斑指数->色斑
            Integer smoothness = getScore(result,"pore");//光滑度->毛孔
            Integer youngDegree = getScore(result,"wrinkle");//年轻度-> 皱纹
            Integer cleanSkin = getScore(result,"acne");//净肤度->痘痘
            List<Integer> efficacyCodes = commodity.getEfficacyCodes();
            boolean flag = hasEfficacy(youngDegree,efficacyCodes,1) || hasEfficacy(colorSpotIndex,efficacyCodes,2) ||
                    hasEfficacy(cleanSkin,efficacyCodes,3) || hasEfficacy(smoothness,efficacyCodes,4);
            if(flag){
                sensitiveScore += 30;
            }else {
                sensitiveScore += 10;
            }
            //风险分
            if(( age!= null && age > 18) || commodity.getSafeGrade() >= 3.5) {
                sensitiveScore += 20;
            }
            String sensitiveDes = null;
            if(sensitiveScore > 80) {
                sensitiveDes = "0";
            }else if(sensitiveScore >=60 && sensitiveScore <= 80){
                sensitiveDes = "1";
            }else{
                sensitiveDes = "2";
            }
            map.put(commodity.getId().toString(),sensitiveDes);
        });
        return map;
    }

    private Integer getScore(Map result,String key){
        Object score = ((Map)result.get(key)).get("score");
        if(score != null){
            return Integer.valueOf(score.toString());
        }
        return null;
    }

    @Override
    public Map computeSuitabilityOne(MemberInfo memberInfo, Integer cid) {
        try {
            Commodity commodity = elasticsearchServer.getById(Commodity.class,cid+"");
            DetectRecord record = detectRecordMapper.selectValidLastTime(memberInfo.getId());
            return computeSuitability(memberInfo,record, Lists.newArrayList(commodity));
        } catch (Exception e) {
            logger.error("computeSuitabilityOne failed,memberId:{},cid:{},error:{}",memberInfo.getId(),cid,e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private boolean hasEfficacy(int score,List<Integer> efficacyCodes,int efficacyCode ){
        return score< 80 && !ObjectUtils.isEmpty(efficacyCodes) && efficacyCodes.indexOf(efficacyCode) > -1;
    }

}
