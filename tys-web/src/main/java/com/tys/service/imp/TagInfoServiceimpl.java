package com.tys.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tys.service.TagInfoService;
import com.tys.service.imp.dao.TagInfoMapper;
import com.tys.entity.vo.TagInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author haoxu
 * @Date 2019/3/6 14:37
 **/
@Service
@Transactional
public class TagInfoServiceimpl implements TagInfoService {

    @Autowired
    private TagInfoMapper tagInfoMapper;

    @Override
    public int insert(TagInfo record) {
        return tagInfoMapper.insertSelective(record);
    }

    @Override
    public int update(TagInfo record) {
        return tagInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteById(Integer id) {
        return tagInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true)
    public TagInfo selectById(Integer id) {
        return tagInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<TagInfo> query(TagInfo record) {
        PageInfo<TagInfo> pageInfo = null;
        if(record.getCurrentPage() != null && record.getPageSize() != null){
            pageInfo = PageHelper.startPage(record.getCurrentPage(), record.getPageSize()).doSelectPageInfo(() -> tagInfoMapper.querySelective(record));
        }else {
            pageInfo = new PageInfo(tagInfoMapper.querySelective(record));
        }
        return pageInfo;
    }
}
