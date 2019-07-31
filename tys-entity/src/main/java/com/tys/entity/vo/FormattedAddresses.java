package com.tys.entity.vo;

import java.io.Serializable;

/**
 * 解析地址
 * @Author haoxu
 * @Date 2019/3/8 13:25
 **/
public class FormattedAddresses implements Serializable {
    String recommend;
    String rough;

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getRough() {
        return rough;
    }

    public void setRough(String rough) {
        this.rough = rough;
    }
}