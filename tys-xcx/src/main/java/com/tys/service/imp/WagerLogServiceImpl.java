package com.tys.service.imp;

import com.tys.entity.vo.WagerLog;
import com.tys.service.WagerLogService;
import com.tys.service.imp.dao.WagerLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WagerLogServiceImpl implements WagerLogService {

    @Autowired
    private WagerLogMapper wagerLogMapper;

    @Override
    @Transactional
    public int insertWagerLog(WagerLog wagerLog) {
        return wagerLogMapper.insertWagerLog(wagerLog);
    }

    @Override
    public WagerLog getNewRecord() {
        return wagerLogMapper.getNewRecord();
    }
}
