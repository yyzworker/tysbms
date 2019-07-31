package com.tys.service.imp;

import com.google.common.collect.Lists;
import com.tys.config.BmsConfig;
import com.tys.constant.Constant;
import com.tys.entity.es.Commodity;
import com.tys.entity.es.Composition;
import com.tys.entity.vo.CommodityVo;
import com.tys.factory.RedisSequenceFactory;
import com.tys.security.util.AuthenticationUtil;
import com.tys.service.CommodityService;
import com.tys.util.es.constant.AssociateType;
import com.tys.util.es.constant.QueryType;
import com.tys.util.es.server.ElasticsearchServer;
import com.tys.util.es.vo.Condition;
import com.tys.util.es.vo.Page;
import com.tys.util.es.vo.QueryInfo;
import io.swagger.models.auth.In;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * @Author haoxu
 * @Date 2019/4/1 11:21
 **/
@Service
public class CommodityServiceImpl implements CommodityService {

    private Logger logger = LoggerFactory.getLogger(CommodityService.class);

    @Autowired
    private ElasticsearchServer elasticsearchServer;

    @Autowired
    private BmsConfig bmsConfig;

    @Autowired
    private RedisSequenceFactory redisSequenceFactory;


    @Override
    public Page<Commodity> search(CommodityVo commodity) {
        QueryInfo queryInfo = new QueryInfo(Commodity.class);
        List<Condition> conditions = Lists.newLinkedList();
        if(commodity.getName() != null){
            conditions.add(new Condition(new String[]{"name","name.standard"},QueryType.MULTI_MATCH,commodity.getName()));
        }
        if(commodity.getTypeCode() != null){
            conditions.add(new Condition(new String[]{"typeCode"},QueryType.TREM,commodity.getTypeCode()));
        }
        if(commodity.getImgUrl() != null){
            if("1".equals(commodity.getImgUrl())){
                conditions.add(new Condition(new String[]{"imgUrl"},QueryType.EXISTS,null));
            }else if("0".equals(commodity.getImgUrl())){
                conditions.add(new Condition(new String[]{"imgUrl"},QueryType.EXISTS,AssociateType.MUST_NOT,null));
            }
        }
        if(commodity.getSuitableSexCode() != null){
            conditions.add(new Condition(new String[]{"suitableSexCode"},QueryType.TREM,commodity.getSuitableSexCode()));
        }
        conditions.add(new Condition(new String[]{"status"},QueryType.TREM,1));
        queryInfo.setConditions(conditions);
        queryInfo.setIncludes(new String[]{"id","imgUrl","name","alias","typeCode","suitableSexCode","fitSensitive"});
        if(commodity.getCurrentPage() != null && commodity.getPageSize() != null){
            queryInfo.setPageNum(commodity.getCurrentPage()-1);
            queryInfo.setPageSize(commodity.getPageSize());
        }
        try {
            return elasticsearchServer.search(queryInfo);
        } catch (IOException e) {
            logger.error("search failed,error:{}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Commodity save(CommodityVo commodity) {
        Commodity entity = new Commodity();
        BeanUtils.copyProperties(commodity,entity);
        computeCommodityData(entity);
        Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
        entity.setId(redisSequenceFactory.generate(bmsConfig.getCommodityPk()));
        entity.setStatus(1);
        String userId = AuthenticationUtil.getUserId();
        if(userId != null ){
            entity.setCreateUserId(Integer.valueOf(userId));
            entity.setUpdateUserId(Integer.valueOf(userId));
        }
        entity.setUpdateTime(now);
        entity.setCreateTime(now);
        try {
            return elasticsearchServer.saveOrUpdateIndex(entity);
        } catch (Exception e) {
            logger.error("save failed,error:{}",e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Commodity update(CommodityVo commodity) {
        Commodity entity = new Commodity();
        BeanUtils.copyProperties(commodity,entity);
        computeCommodityData(entity);
        Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
        String userId = AuthenticationUtil.getUserId();
        if(userId != null ){
            entity.setUpdateUserId(Integer.valueOf(userId));
        }
        entity.setUpdateTime(now);
        try {
            return elasticsearchServer.saveOrUpdateIndex(entity);
        } catch (Exception e) {
            logger.error("save failed,error:{}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private void computeCommodityData(Commodity entity){
        List<Composition> compositions = entity.getCompositions();
        if(ObjectUtils.isEmpty(compositions)){
            entity.setSafeGrade(null);
            entity.setAcneCount(null);
            entity.setEfficacys(null);
            entity.setEfficacyCodes(null);
            entity.setSuitableSkins(null);
            entity.setSuitableSkinCodes(null);
            entity.setFitSensitive(null);
        }else {
            int hasSafe = 0,safeRange = 0,normalRange = 0,dangerRange = 0,fitSensitive = 1;
            List<Integer> efficacyCodes = Lists.newArrayList();
            List<String> efficacys = Lists.newArrayList();
            List<Integer> suitableSkinCodes = Lists.newArrayList();
            List<String> suitableSkins = Lists.newArrayList();
            boolean flag = true;
            Integer acneCount = 0;
            for(int j = 0;j< compositions.size();j++){
                Composition comp = compositions.get(j);
                if(comp.getSafeLevel() != null){
                    hasSafe ++;
                    if(comp.getSafeLevel()>=0 && comp.getSafeLevel() <=2){
                        safeRange++;
                    }else if(comp.getSafeLevel()>=3 && comp.getSafeLevel()<=6){
                        normalRange++;
                    }else {
                        dangerRange++;
                    }
                }
                if(!ObjectUtils.isEmpty(comp.getEfficacyCodes())){
                    efficacyCodes.removeAll(comp.getEfficacyCodes());
                    efficacyCodes.addAll(comp.getEfficacyCodes());
                }
                if(!ObjectUtils.isEmpty(comp.getSkinTypeCodes()) && ObjectUtils.isEmpty(suitableSkinCodes) && flag){
                    suitableSkinCodes = comp.getSkinTypeCodes();
                }else if(!ObjectUtils.isEmpty(comp.getSkinTypeCodes()) && !ObjectUtils.isEmpty(suitableSkinCodes)){
                    suitableSkinCodes.retainAll(comp.getSkinTypeCodes());
                    if(ObjectUtils.isEmpty(suitableSkinCodes)){
                        flag = false;
                    }
                }
                if(comp.getAcne() != null && comp.getAcne() == 1){
                    acneCount++;
                }
                if(comp.getFitSensitive() != null && comp.getFitSensitive() == 0){
                    fitSensitive = 0;
                }
            }
            efficacyCodes.stream().forEachOrdered(efficacyCode -> {
                efficacys.add(Constant.EFFICACY.get(efficacyCode));
            });
            suitableSkinCodes.stream().forEachOrdered(suitableSkinCode -> {
                suitableSkins.add(Constant.SKINTYPE.get(suitableSkinCode));
            });
            if(!ObjectUtils.isEmpty(efficacyCodes)){
                entity.setEfficacyCodes(efficacyCodes);
                entity.setEfficacys(efficacys);
            }else {
                entity.setEfficacyCodes(null);
                entity.setEfficacys(null);
            }
            if(!ObjectUtils.isEmpty(suitableSkinCodes)){
                entity.setSuitableSkinCodes(suitableSkinCodes);
                entity.setSuitableSkins(suitableSkins);
            }else {
                entity.setSuitableSkinCodes(null);
                entity.setSuitableSkins(null);
            }
            if(hasSafe != 0){
                double safeGrade = (safeRange*1+normalRange*0.7+dangerRange*0.3)/hasSafe;
                safeGrade = Math.floor(safeGrade*10)/10;
                entity.setSafeGrade(safeGrade);
            }else {
                entity.setSafeGrade(null);
            }
            entity.setFitSensitive((int)fitSensitive);
            entity.setAcneCount(acneCount);
        }
    }

    @Override
    public Commodity queryById(Integer id) {
        try {
            return elasticsearchServer.getById(Commodity.class,id.toString());
        } catch (Exception e) {
            logger.error("queryById failed,error:{}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        Commodity commodity = new Commodity();
        commodity.setId(id);
        commodity.setStatus(0);
        try {
            elasticsearchServer.saveOrUpdateIndex(commodity);
        } catch (Exception e) {
            logger.error("delete failed,error:{}",e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public int exitsName(String name) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.matchPhraseQuery("name", name));
        qb.must(QueryBuilders.termQuery("status", 1));
        QueryInfo queryInfo = new QueryInfo(Commodity.class);
        List<Condition> conditions = Lists.newLinkedList();
        conditions.add(new Condition(new String[]{"name"},QueryType.MATCH_PHRASE,name));
        conditions.add(new Condition(new String[]{"status"},QueryType.TREM,1));
        queryInfo.setConditions(conditions);
        queryInfo.setIncludes(new String[]{"name"});
        try {
            Page<Commodity> page  = elasticsearchServer.search(queryInfo);
            if(page.getTotal() > 0 ){
                Optional<Commodity> optional = page.getContent().stream().filter(commodity -> name.equalsIgnoreCase(commodity.getName())).findFirst();
                if(optional.isPresent()){
                    return 1;
                }else{
                    return 0;
                }
            }
        } catch (IOException e) {
            logger.error("exitsName failed,error:{}",e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}
