package com.tys.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * 签到实体类
 */
public class UserExpLog {
    @ApiModelProperty(value = "积分事件日志主键", required = false)
    private Integer id;

    @ApiModelProperty(value = "用户主键",required = true)
    private Integer userId;
    @ApiModelProperty(value = "事件时间")
    private Timestamp eventDate;
    @ApiModelProperty(value = "事件ID")
    private Integer eventId;
    @ApiModelProperty(value = "该事件所得积分")
    private Integer point;
    @ApiModelProperty(value = "事件名称")
    private String eventName;

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

    public Timestamp getEventDate() {
        return eventDate;
    }

    public void setEventDate(Timestamp eventDate) {
        this.eventDate = eventDate;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    private String eventType;

}
