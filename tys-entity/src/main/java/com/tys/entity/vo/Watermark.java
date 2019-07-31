package com.tys.entity.vo;

import java.sql.Timestamp;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-3-11 10:06
 */
public class Watermark {
    private Timestamp timestamp;
    private String appid;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
