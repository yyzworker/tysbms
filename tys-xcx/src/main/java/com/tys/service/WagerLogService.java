package com.tys.service;

import com.tys.entity.vo.WagerLog;


public interface WagerLogService {
    /**
     * 添加赌注记录
     * @return
     */
     int insertWagerLog(WagerLog wagerLog);

    /**
     * 获取最新赌注记录
     * @return
     */
    WagerLog getNewRecord();

}
