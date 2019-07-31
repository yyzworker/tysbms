package com.tys.service;

import com.tys.entity.vo.EmAdvertise;

import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/7/12 15:58
 **/
public interface EmAdvertiseService {

    List<EmAdvertise> selectEmAdvertiseByEmId(Integer emId);

    int insertEmAdvertise(EmAdvertise emAdvertise);

    int updateMultipleEmAdvertise(EmAdvertise emAdvertise);
}
