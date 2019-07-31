package com.tys.service;

import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.DrComplexSetting;

public interface DrComplexService {

    PageInfo<DrComplexSetting> queryDrComplex(DrComplexSetting record);

    int updateDrComplex(DrComplexSetting record);
}
