package com.tys.service;

import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.DrProblemSetting;

public interface DrProblemService {

    PageInfo<DrProblemSetting> queryDrProblems(DrProblemSetting record);

    int updateDrProblem(DrProblemSetting record);
}
