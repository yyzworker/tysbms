package com.tys.service;

import com.tys.entity.vo.UserExpLog;

public interface UserExpLogService {
    /**
     * 添加分享记录
     * @param record
     * @return
     */
    int insertUserExpLog( UserExpLog record);
}
