package com.tys.service.imp;

import com.tys.entity.vo.UserSignLog;
import com.tys.service.UserSignLogService;
import com.tys.service.imp.dao.UserSignLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserSignLogServiceImpl implements UserSignLogService {
    @Autowired
    private UserSignLogMapper userSignLogMapper;
    @Override
    @Transactional
    public int insertUserSignLog(UserSignLog userSignLog) {
        return userSignLogMapper.insertUserSignLog(userSignLog);
    }
}
