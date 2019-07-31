package com.tys.consumer;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.tys.constant.Constant;
import com.tys.entity.es.Composition;
import org.apache.http.Header;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @Author haoxu
 * @Date 2019/4/10 13:50
 **/
public class CommodityConsumer implements Runnable {

    private Logger logger = LoggerFactory.getLogger(CommodityConsumer.class);

    private RestHighLevelClient restHighLevelClient;

    private Composition composition;

    /**
     * scroll游标快照超时时间，单位ms
     */
    private static final long SCROLL_TIMEOUT = 3000;

    public CommodityConsumer(RestHighLevelClient restHighLevelClient, Composition composition) {
        this.restHighLevelClient = restHighLevelClient;
        this.composition = composition;
    }

    @Override
    public void run() {
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        bqb.must(QueryBuilders.nestedQuery("compositions",QueryBuilders.termQuery("compositions.id",composition.getId()), ScoreMode.Max));
        SearchRequest searchRequest = new SearchRequest("index_commodity").types("commodity");
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(bqb);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
            String scrollId = searchResponse.getScrollId();
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            BulkRequest bulkRequest = new BulkRequest();
            while (searchHits != null && searchHits.length > 0) {
                Arrays.stream(searchHits).forEach(searchHit ->{
                    try {
                        Map hit = searchHit.getSourceAsMap();
                        List<Map> compositions = (List<Map>) hit.get("compositions");
                        int hasSafe = 0,safeRange = 0,normalRange = 0,dangerRange = 0,fitSensitive = 1;
                        List efficacyCodes = Lists.newArrayList();
                        List efficacys = Lists.newArrayList();
                        List suitableSkinCodes = Lists.newArrayList();
                        List suitableSkins = Lists.newArrayList();
                        boolean flag = true;
                        Integer acneCount = 0;
                        for(int j = 0;j< compositions.size();j++){
                            Map comp = compositions.get(j);
                            if (comp.get("id").equals(composition.getId())) {
                                Map replace = (Map)JSON.toJSON(composition);
                                if(replace.get("createTime") != null){
                                    replace.put("createTime",((Timestamp)replace.get("createTime")).getTime());
                                }
                                if(replace.get("updateTime") != null){
                                    replace.put("updateTime",((Timestamp)replace.get("updateTime")).getTime());
                                }
                                Collections.replaceAll(compositions,comp,replace);
                                comp = replace;
                            }
                            if (comp.get("safeLevel") != null) {
                                int safeLevel = Integer.valueOf(comp.get("safeLevel").toString());
                                hasSafe++;
                                if (safeLevel >= 0 && safeLevel <= 2) {
                                    safeRange++;
                                } else if (safeLevel >= 3 && safeLevel <= 6) {
                                    normalRange++;
                                } else {
                                    dangerRange++;
                                }
                            }
                            if (!ObjectUtils.isEmpty(comp.get("efficacyCodes"))) {
                                List eCodes = (List) comp.get("efficacyCodes");
                                efficacyCodes.removeAll(eCodes);
                                efficacyCodes.addAll(eCodes);
                            }
                            if (!ObjectUtils.isEmpty(comp.get("skinTypeCodes")) && ObjectUtils.isEmpty(suitableSkinCodes) && flag) {
                                suitableSkinCodes = (List) comp.get("skinTypeCodes");
                            } else if (!ObjectUtils.isEmpty(comp.get("skinTypeCodes")) && !ObjectUtils.isEmpty(suitableSkinCodes)) {
                                suitableSkinCodes.retainAll((List) comp.get("skinTypeCodes"));
                                if (ObjectUtils.isEmpty(suitableSkinCodes)) {
                                    flag = false;
                                }
                            }
                            if (comp.get("acne") != null && Integer.valueOf(comp.get("acne").toString()) == 1) {
                                acneCount++;
                            }
                            if (comp.get("fitSensitive") !=null && Integer.valueOf(comp.get("fitSensitive").toString()) == 0) {
                                fitSensitive = 0;
                            }
                        }
                        efficacyCodes.stream().forEachOrdered(efficacyCode -> {
                            efficacys.add(Constant.EFFICACY.get(Integer.valueOf(efficacyCode.toString())));
                        });
                        suitableSkinCodes.stream().forEachOrdered(suitableSkinCode -> {
                            suitableSkins.add(Constant.SKINTYPE.get(Integer.valueOf(suitableSkinCode.toString())));
                        });
                        if (!ObjectUtils.isEmpty(efficacyCodes)) {
                            hit.put("efficacyCodes",efficacyCodes);
                            hit.put("efficacys",efficacys);
                        } else {
                            hit.put("efficacyCodes",null);
                            hit.put("efficacys",null);
                        }
                        if (!ObjectUtils.isEmpty(suitableSkinCodes)) {
                            hit.put("suitableSkinCodes",suitableSkinCodes);
                            hit.put("suitableSkins",suitableSkins);
                        } else {
                            hit.put("suitableSkinCodes",null);
                            hit.put("suitableSkins",null);
                        }
                        if (hasSafe != 0) {
                            double safeGrade = (safeRange * 1 + normalRange * 0.7 + dangerRange * 0.3) / hasSafe;
                            safeGrade = Math.floor(safeGrade * 10) / 10;
                            hit.put("safeGrade",safeGrade);
                        } else {
                            hit.put("safeGrade",null);
                        }
                        hit.put("fitSensitive",(Integer) fitSensitive);
                        hit.put("acneCount",acneCount);
                        hit.put("updateTime",System.currentTimeMillis());
                        bulkRequest.add(new IndexRequest("index_commodity", "commodity", hit.get("id").toString()).source(hit));
                    }catch (Exception e){
                        logger.error("update compositions failed,compid:{},error:{}",composition.getId(),e.getMessage());
                        e.printStackTrace();
                    }
                });
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                searchResponse = restHighLevelClient.searchScroll(scrollRequest);
                scrollId = searchResponse.getScrollId();
                searchHits = searchResponse.getHits().getHits();
            }
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, new Header[]{});
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    logger.error("update compositions failed, type: {} ,id: {} ,cause by: {}",failure.getType(), failure.getId(), Throwables.getStackTraceAsString(failure.getCause()));
                }
            }
            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            ClearScrollResponse clearScrollResponse = restHighLevelClient.clearScroll(clearScrollRequest);
            boolean succeeded = clearScrollResponse.isSucceeded();
            if(!succeeded){
                restHighLevelClient.clearScroll(clearScrollRequest);
            }
        } catch (IOException e) {
            logger.error("update compositions failed,compid:{},error:{}",composition.getId(),e.getMessage());
            e.printStackTrace();
        }
    }
}
