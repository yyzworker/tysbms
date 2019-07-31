package com.tys.service.imp;

import com.tys.entity.vo.TagInfo;
import com.tys.service.TagInfoService;
import com.tys.service.imp.dao.TagInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/3/19 16:22
 **/
@Service
public class TagInfoServiceImpl implements TagInfoService {

    @Autowired
    private TagInfoMapper xcxTagInfoMapper;

    private Logger logger = LoggerFactory.getLogger(TagInfoService.class);

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="tagijnfos",key="#type")
    public List<String> getTagInfos(Integer type) {
        try {
            return xcxTagInfoMapper.getTagInfos(type);
        } catch (Exception e) {
            logger.error("获得标签错误，type:{},error:{}", type, e.getMessage());
        }
        return null;
    }

    @Override
    @Cacheable(value="tagsijnfos",key="#types")
    public List<TagInfo> getTagsInfos(List<Integer> types) {
        try {
            return xcxTagInfoMapper.getTagsInfos(types);
        } catch (Exception e) {
            logger.error("获得标签错误，types:{},error:{}", Arrays.toString(types.toArray()), e.getMessage());
        }
        return null;
    }
}
