package com.tys.service.imp;

import com.google.common.collect.Lists;
import com.tys.config.BmsConfig;
import com.tys.consumer.CommodityConsumer;
import com.tys.entity.es.Commodity;
import com.tys.entity.es.Composition;
import com.tys.entity.vo.CompositionVo;
import com.tys.factory.RedisSequenceFactory;
import com.tys.service.CompositionService;
import com.tys.util.es.constant.AssociateType;
import com.tys.util.es.constant.QueryType;
import com.tys.util.es.server.ElasticsearchServer;
import com.tys.util.es.vo.Condition;
import com.tys.util.es.vo.Page;
import com.tys.util.es.vo.QueryInfo;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author haoxu
 * @Date 2019/3/28 16:25
 **/
@Service
public class CompositionServiceImpl implements CompositionService {

    private Logger logger = LoggerFactory.getLogger(CompositionService.class);

    @Autowired
    private ElasticsearchServer elasticsearchServer;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private BmsConfig bmsConfig;

    @Autowired
    private RedisSequenceFactory redisSequenceFactory;

    private ExecutorService service = Executors.newFixedThreadPool(10);

    public Page<Composition> search(CompositionVo composition) {
        QueryInfo queryInfo = new QueryInfo(Composition.class);
        List<Condition> conditions = Lists.newLinkedList();
        if (composition.getAcne() != null) {
            conditions.add(new Condition(new String[]{"acne"}, QueryType.TREM, composition.getAcne()));
        }
        if (composition.getActive() != null) {
            conditions.add(new Condition(new String[]{"active"}, QueryType.TREM, composition.getActive()));
        }
        if (composition.getName() != null) {
            conditions.add(new Condition(new String[]{"name"}, QueryType.MATCH_PHRASE, composition.getName()));
        }
        if (composition.getSafeLevel() != null) {
            conditions.add(new Condition(new String[]{"safeLevel"}, QueryType.TREM, composition.getSafeLevel()));
        }
        if (composition.getUsedAimStr() != null) {
            int value = Integer.valueOf(composition.getUsedAimStr());
            switch (value) {
                case 1:
                    conditions.add(new Condition(new String[]{"usedAim"}, QueryType.EXISTS, AssociateType.MUST_NOT, null));
                    break;
                case 2:
                    conditions.add(new Condition(new String[]{"usedAim"}, QueryType.EXISTS, null));
                    break;
            }
        }
        conditions.add(new Condition(new String[]{"status"}, QueryType.TREM, 1));
        queryInfo.setConditions(conditions);
        if (composition.getCurrentPage() != null && composition.getPageSize() != null) {
            queryInfo.setPageNum(composition.getCurrentPage()-1);
            queryInfo.setPageSize(composition.getPageSize());
        }
        try {
            return elasticsearchServer.search(queryInfo);
        } catch (IOException e) {
            logger.error("search failed,error:{}", e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Composition save(CompositionVo composition) {
        Composition entity = new Composition();
        BeanUtils.copyProperties(composition, entity);
        Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
        entity.setId(redisSequenceFactory.generate(bmsConfig.getCompositionPk()));
        entity.setStatus( 1);
        entity.setUpdateTime(now);
        entity.setCreateTime(now);
        try {
            return elasticsearchServer.saveOrUpdateIndex(entity);
        } catch (Exception e) {
            logger.error("save failed,error:{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Composition update(CompositionVo composition) {
        Composition entity = new Composition();
        BeanUtils.copyProperties(composition, entity);
        Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
        entity.setUpdateTime(now);
        service.submit(new CommodityConsumer(restHighLevelClient, entity));
        try {
            return elasticsearchServer.saveOrUpdateIndex(entity);
        } catch (Exception e) {
            logger.error("update failed,error:{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Composition queryById(Integer id) {
        try {
            return elasticsearchServer.getById(Composition.class, id.toString());
        } catch (Exception e) {
            logger.error("queryById failed,error:{}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int delete(Integer id) {
        QueryInfo queryInfo = new QueryInfo(Commodity.class);
        queryInfo.setIncludes(new String[]{"id"});
        List<Condition> conditions = Lists.newLinkedList();
        conditions.add(new Condition(new String[]{"compositions"}, QueryType.NESTED, AssociateType.MUST, new Condition(new String[]{"compositions.id"}, QueryType.TREM, id), null));
        conditions.add(new Condition(new String[]{"status"}, QueryType.TREM, 1));
        queryInfo.setConditions(conditions);
        try {
            Page<Commodity> page = elasticsearchServer.search(queryInfo);
            int total = page.getTotal();
            if (total > 0) {
                return total;
            }
            Composition composition = new Composition();
            composition.setId(id);
            composition.setStatus(0);
            elasticsearchServer.saveOrUpdateIndex(composition);
        } catch (Exception e) {
            logger.error("delete failed,error:{}", e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }


}
