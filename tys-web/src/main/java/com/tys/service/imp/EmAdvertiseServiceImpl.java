package com.tys.service.imp;

import com.tys.entity.vo.EmAdvertise;
import com.tys.service.EmAdvertiseService;
import com.tys.service.imp.dao.EmAdvertiseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/7/12 15:58
 **/
@Service
public class EmAdvertiseServiceImpl implements EmAdvertiseService {

    @Autowired
    private EmAdvertiseMapper emAdvertiseMapper;


    @Override
    public List<EmAdvertise> selectEmAdvertiseByEmId(Integer emId) {
        return emAdvertiseMapper.selectByEmId(emId);
    }

    @Override
    public int insertEmAdvertise(EmAdvertise emAdvertise) {
        return emAdvertiseMapper.insertEmAdvertise(emAdvertise);
    }

    @Override
    public int updateMultipleEmAdvertise(EmAdvertise emAdvertise) {
        return emAdvertiseMapper.updateMultipleEmAdvertise(emAdvertise);
    }
}
