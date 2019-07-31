package com.tys.service;

import com.tys.entity.vo.Feedback;
import com.tys.entity.vo.Goods;

import java.util.List;

public interface GoodsService {

    public int insertGoods(Goods goods);

    public List<Goods> getGoodsList();

    public Goods getGoodsById(Integer id);



}
