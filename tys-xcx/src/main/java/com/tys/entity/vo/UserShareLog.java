package com.tys.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * 用户分享
 */
public class UserShareLog {
    @ApiModelProperty(value = "积分事件日志主键", required = false)
    private Integer id;
    @ApiModelProperty(value = "用户主键",required = true)
    private Integer userId;
    @ApiModelProperty(value = "分享时间")
    private Timestamp shareDate;

    @ApiModelProperty(value = "分享任务ID号")
    private Integer rwId;

    public Integer getRwId() {
        return rwId;
    }

    public void setRwId(Integer rwId) {
        this.rwId = rwId;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getShareDate() {
        return shareDate;
    }

    public void setShareDate(Timestamp shareDate) {
        this.shareDate = shareDate;
    }
}
