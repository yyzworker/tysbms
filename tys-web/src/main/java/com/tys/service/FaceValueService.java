package com.tys.service;

import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.FaceValue;

/**
 * @Author haoxu
 * @Date 2019/3/6 13:53
 **/
public interface FaceValueService {

    int insert(FaceValue faceValue);

    int deleteById(Integer id);

    int update(FaceValue faceValue);

    FaceValue selectById(Integer id);

    PageInfo<FaceValue> query(FaceValue faceValue);
}
