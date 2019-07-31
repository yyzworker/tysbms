package com.tys.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tys.service.FaceValueService;
import com.tys.service.imp.dao.FaceValueMapper;
import com.tys.entity.vo.FaceValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author haoxu
 * @Date 2019/3/6 13:53
 **/
@Service
@Transactional
public class FaceValueServiceImpl implements FaceValueService {

    @Autowired
    private FaceValueMapper faceValueMapper;

    @Override
    public int insert(FaceValue faceValue) {
        return faceValueMapper.insertSelective(faceValue);
    }

    @Override
    public int deleteById(Integer id) {
        return faceValueMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(FaceValue faceValue) {
        return faceValueMapper.updateByPrimaryKeySelective(faceValue);
    }

    @Override
    @Transactional(readOnly = true)
    public FaceValue selectById(Integer id) {
        return faceValueMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<FaceValue> query(FaceValue record) {
        PageInfo<FaceValue> pageInfo = null;
        if(record.getCurrentPage() != null && record.getPageSize() != null){
            pageInfo = PageHelper.startPage(record.getCurrentPage(),record.getPageSize()).doSelectPageInfo(() -> faceValueMapper.querySelective(record));
        }else
            pageInfo = new PageInfo<>(faceValueMapper.querySelective(record));
        return pageInfo;
    }
}
