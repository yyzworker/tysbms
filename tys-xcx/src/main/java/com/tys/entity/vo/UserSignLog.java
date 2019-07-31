package com.tys.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * 签到实体类
 */
public class UserSignLog {
    @ApiModelProperty(value = "签到主键", required = false)
    private Integer id;

    @ApiModelProperty(value = "用户主键",required = true)
    private Integer userId;

    private Timestamp signDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getSignDate() {
        return signDate;
    }

    public void setSignDate(Timestamp signDate) {
        this.signDate = signDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
