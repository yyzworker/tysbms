package com.tys.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.DrComplexSetting;
import com.tys.service.DrComplexService;
import com.tys.service.imp.dao.DrComplexSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author haoxu
 * @Date 2019/3/25 17:28
 **/
@Service
@Transactional
public class DrComplexServiceImpl implements DrComplexService {

    @Autowired
    private DrComplexSettingMapper complexSettingMapper;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<DrComplexSetting> queryDrComplex(DrComplexSetting record) {
        PageInfo<DrComplexSetting> pageInfo = null;
        if(record.getCurrentPage() != null && record.getPageSize() != null){
            pageInfo = PageHelper.startPage(record.getCurrentPage(),record.getPageSize()).doSelectPageInfo(() -> complexSettingMapper.query(record));
        }else
            pageInfo = new PageInfo<>(complexSettingMapper.query(record));
        return pageInfo;
    }

    @Override
    public int updateDrComplex(DrComplexSetting record) {
        return complexSettingMapper.updateByPrimaryKeySelective(record);
    }
}
