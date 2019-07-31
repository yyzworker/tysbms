package com.tys.entity.vo;

import io.swagger.annotations.ApiModelProperty;

public class WagerStatus {
    @ApiModelProperty(value = "赌注ID",required = true)
    private Integer wagerId;

    @ApiModelProperty(value = "买赢总人数",required = true)
    private Long upCount;

    @ApiModelProperty(value = "买跌总人数",required = true)
    private Long downCount;

    @ApiModelProperty(value = "还剩多少秒",required = true)
    private Long secCount;

    @ApiModelProperty(value = "自己是否已经下注(0(未下注)/1(已下注))",required = true)
    private String done;

    public Integer getWagerId() {
        return wagerId;
    }

    public Long getUpCount() {
        return upCount;
    }

    public void setUpCount(Long upCount) {
        this.upCount = upCount;
    }

    public Long getDownCount() {
        return downCount;
    }

    public void setDownCount(Long downCount) {
        this.downCount = downCount;
    }

    public Long getSecCount() {
        return secCount;
    }

    public void setSecCount(Long secCount) {
        this.secCount = secCount;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWagerId(Integer wagerId) {
        this.wagerId = wagerId;
    }

    @ApiModelProperty(value = "下注类型(0(买跌)/1(买涨))",required = true)
    private String type;

}
