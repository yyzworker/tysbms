package com.tys.service.imp;

import com.tys.entity.vo.Goods;
import com.tys.entity.vo.UserGoodsLog;
import com.tys.service.UserGoodsLogService;
import com.tys.service.imp.dao.UserGoodsLogMapper;
import org.springframework.stereotype.Service;

@Service
public class UserGoodsLogServiceImpl implements UserGoodsLogService {
    private UserGoodsLogMapper userGoodsLogMapper;
    @Override
    public int insertUserGoodsLog(UserGoodsLog userGoodsLog) {
        return userGoodsLogMapper.insertUserGoodsLog(userGoodsLog);
    }
}
