package com.tys.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.DrProblemSetting;
import com.tys.service.DrProblemService;
import com.tys.service.imp.dao.DrProblemSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author haoxu
 * @Date 2019/3/25 17:29
 **/
@Service
@Transactional
public class DrProblemServiceImpl implements DrProblemService {

    @Autowired
    private DrProblemSettingMapper problemSettingMapper;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<DrProblemSetting> queryDrProblems(DrProblemSetting record) {
        PageInfo<DrProblemSetting> pageInfo = null;
        if (record.getCurrentPage() != null && record.getPageSize() != null) {
            pageInfo = PageHelper.startPage(record.getCurrentPage(), record.getPageSize()).doSelectPageInfo(() -> problemSettingMapper.query(record));
        } else
            pageInfo = new PageInfo<>(problemSettingMapper.query(record));
        return pageInfo;
    }

    @Override
    public int updateDrProblem(DrProblemSetting record) {
        return problemSettingMapper.updateByPrimaryKeySelective(record);
    }
}
