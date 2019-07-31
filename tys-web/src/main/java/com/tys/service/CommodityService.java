package com.tys.service;

import com.tys.entity.es.Commodity;
import com.tys.entity.vo.CommodityVo;
import com.tys.util.es.vo.Page;

public interface CommodityService {

    Page<Commodity> search(CommodityVo commodity);

    Commodity save(CommodityVo commodity);

    Commodity update(CommodityVo commodity);

    Commodity queryById(Integer id);

    void delete(Integer id);

    int exitsName(String name);
}
