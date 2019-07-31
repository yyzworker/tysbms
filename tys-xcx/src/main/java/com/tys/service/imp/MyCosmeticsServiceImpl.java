package com.tys.service.imp;

import com.tys.entity.es.Commodity;
import com.tys.entity.vo.MyCosmeticsVo;
import com.tys.service.MyCosmeticsService;
import com.tys.service.imp.dao.MyCosmeticsMapper;
import com.tys.util.es.server.ElasticsearchServer;
import com.tys.util.es.vo.QueryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class MyCosmeticsServiceImpl implements MyCosmeticsService {

    private Logger logger = LoggerFactory.getLogger(MyCosmeticsService.class);

    @Autowired
    private MyCosmeticsMapper myCosmeticsMapper;

    @Autowired
    private ElasticsearchServer elasticsearchServer;

    public static final String CACHE_NAME = "MyCosmeticsCache";

    @Override
    @Transactional
    @CachePut(value = CACHE_NAME ,key = "#record.memberId")
    public List<MyCosmeticsVo> insertMyCosmetics(MyCosmeticsVo record) {
        myCosmeticsMapper.insertMyCosmetics(record);
        return queryMyCosmetics(record.getMemberId());
    }

    @Override
    @Transactional
    @CachePut(value = CACHE_NAME ,key = "#record.memberId")
    public List<MyCosmeticsVo> deleteMyCosmetics(MyCosmeticsVo record) {
         myCosmeticsMapper.deleteMyCosmetics(record);
         return queryMyCosmetics(record.getMemberId());
    }

    @Override
    @Cacheable(value = CACHE_NAME ,key="#memberId")
    public List<MyCosmeticsVo> queryMyCosmetics(Integer memberId) {
        return myCosmeticsMapper.queryMyCosmetics(memberId);
    }

    @Override
    public List<Commodity> searchEsByIds(List<MyCosmeticsVo> myCosmeticsVosList) {
        List<String> list=new ArrayList<>();
        for(MyCosmeticsVo myCosmeticsVo:myCosmeticsVosList){
            list.add(myCosmeticsVo.getCosmeticId());
        }
        int size=list.size();
        int pageSize = size == 0 ? 1:size;
        String[] array = list.toArray(new String[size]);
        if(ObjectUtils.isEmpty(array)){
            return null;
        }
        QueryInfo queryInfo = new QueryInfo(Commodity.class);
        try {
            return elasticsearchServer.getByIds(queryInfo,array);
        } catch (Exception e) {
            logger.error("searchEsByIds error,ids:{},error:{}", Arrays.toString(array),e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
