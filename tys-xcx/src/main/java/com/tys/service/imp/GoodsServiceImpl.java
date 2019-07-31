package com.tys.service.imp;

import com.tys.entity.vo.Goods;
import com.tys.service.GoodsService;
import com.tys.service.imp.dao.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {



    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public int insertGoods(Goods goods) {
        return 0;
    }

    @Override
    public List<Goods> getGoodsList() {
        return goodsMapper.getGoodsList();
    }

    @Override
    public Goods getGoodsById(Integer id) {
        return goodsMapper.getGoodsById(id);
    }
}
