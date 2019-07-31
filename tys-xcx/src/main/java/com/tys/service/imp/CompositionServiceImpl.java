package com.tys.service.imp;

import com.google.common.collect.Lists;
import com.tys.entity.es.Composition;
import com.tys.service.CompositionService;
import com.tys.util.es.constant.QueryType;
import com.tys.util.es.server.ElasticsearchServer;
import com.tys.util.es.vo.Condition;
import com.tys.util.es.vo.Page;
import com.tys.util.es.vo.QueryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-3-30 10:13
 */
@Service
public class CompositionServiceImpl implements CompositionService {

    private Logger logger = LoggerFactory.getLogger(CompositionService.class);

    @Autowired
    private ElasticsearchServer elasticsearchServer;

    @Override
    public Page<Composition> search(String name, int pageNumber){
        QueryInfo queryInfo = new QueryInfo(Composition.class);
        queryInfo.setPageSize(10);
        queryInfo.setPageNum(pageNumber);
        List<Condition> conditions = Lists.newLinkedList();
        try {
            if(name != null){
                conditions.add(new Condition(new String[]{"name"}, QueryType.MATCH,name));
            }
            conditions.add(new Condition(new String[]{"status"},QueryType.TREM,1));
            queryInfo.setConditions(conditions);
            return elasticsearchServer.search(queryInfo);
        }catch (Exception e){
            logger.error("成分搜索错误，name:{},pageNumber:{},error:{}",name,pageNumber,e.getMessage());
        }
        return null;
    }

}
