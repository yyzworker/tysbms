package com.tys.service;
import com.tys.entity.vo.UserShareLog;
public interface UserShareLogService {

    /**
     * 添加分享记录
     * @param record
     * @return
     */
    int insertUserShareLog( UserShareLog record);

    /**
     * 获取用户分享总数
     * @param userId
     * @return
     */
    int getUserShareCount (Integer userId,Integer rwId);
}
