package com.tys.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * 赌注记录表
 */
public class WagerLog {
    @ApiModelProperty(value = "赌注记录ID", required = false)
    private Integer id;

    @ApiModelProperty(value = "名称", required = false)
    private String name;

    @ApiModelProperty(value = "开始时间", required = false)
    private Timestamp beginDate;

    @ApiModelProperty(value = "结束时间", required = false)
    private Timestamp endDate;

    @ApiModelProperty(value = "开奖时间", required = false)
    private Timestamp lotteryTime;

    @ApiModelProperty(value = "结果", required = false)
    private String result;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Timestamp beginDate) {
        this.beginDate = beginDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Timestamp getLotteryTime() {
        return lotteryTime;
    }

    public void setLotteryTime(Timestamp lotteryTime) {
        this.lotteryTime = lotteryTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Float getWagerValue() {
        return wagerValue;
    }

    public void setWagerValue(Float wagerValue) {
        this.wagerValue = wagerValue;
    }

    @ApiModelProperty(value = "当前赌数值", required = false)
    private  Float wagerValue;
}
