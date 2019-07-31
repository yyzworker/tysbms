package com.tys.service;

import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.CarouselmapVo;

import java.util.List;

public interface CarouselmapService {
    int insertCarouselmap(CarouselmapVo carouselmap);

    PageInfo<CarouselmapVo> queryCarouselmaps(CarouselmapVo carouselmap);

    int updateCarouselmap(CarouselmapVo carouselmap);

    CarouselmapVo getCarouselmapById(Integer id);

    int deleteCarouselmapById(Integer id);

    int exitsName(Integer id,String name);

}
