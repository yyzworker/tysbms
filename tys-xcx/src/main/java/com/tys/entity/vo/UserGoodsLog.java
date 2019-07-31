package com.tys.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

public class UserGoodsLog {
    @ApiModelProperty(value = "用户购买记录ID")
    private Integer id;
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    @ApiModelProperty(value = "奖口ID")
    private Integer goodsId;
    @ApiModelProperty(value = "购买时间")
    private Timestamp createDate;
    @ApiModelProperty(value = "花费积分")
    private Integer point;
    @ApiModelProperty(value = "说明")
    private String demo;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "用户称呼")
    private String name;
    @ApiModelProperty(value = "收件地址")
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
