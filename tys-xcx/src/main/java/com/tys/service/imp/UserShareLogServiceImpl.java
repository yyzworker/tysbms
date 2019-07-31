package com.tys.service.imp;

import com.tys.entity.vo.UserShareLog;
import com.tys.service.UserShareLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tys.service.imp.dao.UserShareLogMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserShareLogServiceImpl implements UserShareLogService {

    @Autowired
    private UserShareLogMapper userShareLogMapper;

    @Override
    public int getUserShareCount(Integer userId,Integer rwId) {
        return userShareLogMapper.getUserShareCount(userId, rwId);
    }

    @Override
    @Transactional
    public int insertUserShareLog(UserShareLog record) {
        return userShareLogMapper.insertUserSignLog(record);
    }
}
