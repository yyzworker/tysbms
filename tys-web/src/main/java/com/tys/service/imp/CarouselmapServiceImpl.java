package com.tys.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.CarouselmapVo;
import com.tys.service.CarouselmapService;
import com.tys.service.imp.dao.CarouselmapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarouselmapServiceImpl implements CarouselmapService {
    @Autowired
    private CarouselmapMapper carouselmapMapper;

    @Override
    public int insertCarouselmap(CarouselmapVo carouselmap) {
        return carouselmapMapper.insertCarouselmap(carouselmap);
    }


    @Override
    public PageInfo<CarouselmapVo> queryCarouselmaps(CarouselmapVo carouselmap) {
        PageInfo<CarouselmapVo> pageInfo = null;
        if(carouselmap.getCurrentPage() != null && carouselmap.getPageSize() != null){
            pageInfo = PageHelper.startPage(carouselmap.getCurrentPage(), carouselmap.getPageSize(),"ID DESC").doSelectPageInfo(() -> carouselmapMapper.queryCarouselmaps(carouselmap));
        }else {
            pageInfo = new PageInfo(carouselmapMapper.queryCarouselmaps(carouselmap));
        }
        return pageInfo;
    }

    @Override
    public int updateCarouselmap(CarouselmapVo carouselmap) {
        return carouselmapMapper.updateByPrimaryKey(carouselmap);
    }

    @Override
    public CarouselmapVo getCarouselmapById(Integer id) {
        return carouselmapMapper.selectCarouselmapById(id);
    }

    @Override
    public int exitsName(Integer id,String name) {
        return carouselmapMapper.exitsName(id,name).size();
    }

    @Override
    public int deleteCarouselmapById(Integer id) {
        return carouselmapMapper.deleteCarouselmapById(id);
    }


}
