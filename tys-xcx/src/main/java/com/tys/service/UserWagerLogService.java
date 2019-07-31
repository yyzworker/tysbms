package com.tys.service;

import com.tys.entity.vo.UserWagerLog;

import java.util.List;
import java.util.Map;

public interface UserWagerLogService {
    /**
     * 添加赌注记录
     * @return
     */
    int insertUserWagerLog(UserWagerLog UserWagerLog);

    List<UserWagerLog> getUserWagerLogList(Integer userId);

    List<Map<String,Object>> getUserWagerByWagerId(Integer wagerId);

    UserWagerLog getUserWagerLogByUserId(Integer userId,Integer wagerId);
}
