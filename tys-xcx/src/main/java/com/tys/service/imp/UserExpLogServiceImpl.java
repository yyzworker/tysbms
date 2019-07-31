package com.tys.service.imp;

import com.tys.entity.vo.UserExpLog;
import com.tys.service.UserExpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import com.tys.service.imp.dao.UserExpLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserExpLogServiceImpl implements UserExpLogService {
    @Autowired
    private UserExpLogMapper userExpLogMapper;
    @Override
    @Transactional
    public int insertUserExpLog(UserExpLog record) {
        return userExpLogMapper.insertExpLog(record);
    }
}
