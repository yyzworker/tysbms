package com.tys.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

public class Goods {
    @ApiModelProperty(value = "奖品ID")
    private Integer id;
    @ApiModelProperty(value = "奖品名称")
    private String name;
    @ApiModelProperty(value = "所需要积分")
    private Integer point;
    @ApiModelProperty(value = "所需要连胜次数")
    private Integer winTime;
    @ApiModelProperty(value = "创建时间")
    private Timestamp createDate;
    @ApiModelProperty(value = "物品说明")
    private String demo;
    @ApiModelProperty(value = "物品剩余个数")
    private Integer num;

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

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getWinTime() {
        return winTime;
    }

    public void setWinTime(Integer winTime) {
        this.winTime = winTime;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


}
