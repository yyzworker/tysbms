package com.tys.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

public class UserWagerLog {
    @ApiModelProperty(value = "下注记录ID", required = false)
    private Integer id;

    @ApiModelProperty(value = "用户主键",required = true)
    private Integer userId;

    @ApiModelProperty(value = "赌注ID",required = true)
    private Integer wagerId;

    @ApiModelProperty(value = "投赌分值",required = true)
    private Integer usePoint;

    @ApiModelProperty(value = "投赌时间", required = false)
    private Timestamp createDate;

    @ApiModelProperty(value = "投赌类型(0(买跌)/1(买涨))", required = false)
    private String type;

    @ApiModelProperty(value = "赌注结果(0(输)/1(赢))", required = false)
    private String result;

    @ApiModelProperty(value = "所得积分", required = false)
    private Integer takePoint;

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

    public Integer getWagerId() {
        return wagerId;
    }

    public void setWagerId(Integer wagerId) {
        this.wagerId = wagerId;
    }

    public Integer getUsePoint() {
        return usePoint;
    }

    public void setUsePoint(Integer usePoint) {
        this.usePoint = usePoint;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getTakePoint() {
        return takePoint;
    }

    public void setTakePoint(Integer takePoint) {
        this.takePoint = takePoint;
    }
}
