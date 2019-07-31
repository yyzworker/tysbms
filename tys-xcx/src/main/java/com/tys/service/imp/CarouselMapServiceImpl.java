package com.tys.service.imp;

import com.tys.entity.vo.CarouselmapVo;
import com.tys.service.CarouselMapService;
import com.tys.service.imp.dao.CarouselMapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/5/29 14:53
 **/
@Service
public class CarouselMapServiceImpl implements CarouselMapService {

    @Autowired
    private CarouselMapMapper carouselMapMapper;

    @Override
    public List<CarouselmapVo> selectEffective() {
        return carouselMapMapper.selectEffective();
    }
}
