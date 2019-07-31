package com.tys.service.imp;

import com.tys.entity.vo.UserWagerLog;
import com.tys.service.UserWagerLogService;
import com.tys.service.imp.dao.UserWagerLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserWagerLogServiceImpl implements UserWagerLogService {

    @Autowired
    private UserWagerLogMapper userWagerLogMapper;
    @Override
    @Transactional
    public int insertUserWagerLog(UserWagerLog userWagerLog) {
        return userWagerLogMapper.insertUserWagerLog(userWagerLog);
    }

    @Override
    public List<UserWagerLog> getUserWagerLogList(Integer userId) {
        return userWagerLogMapper.getUserWagerLogList(userId);
    }

    @Override
    public List<Map<String, Object>> getUserWagerByWagerId(Integer wagerId) {
        return userWagerLogMapper.getUserWagerByWagerId(wagerId);
    }

    @Override
    public UserWagerLog getUserWagerLogByUserId(Integer userId, Integer wagerId) {
        return userWagerLogMapper.getUserWagerLogByUserId(userId,wagerId);
    }
}
