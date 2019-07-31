package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author ：王志浩
 * @date ：Created in 2019-5-7 09:31
 */
@ApiModel(description = "数据中心")
public class GrowthTrendVo implements Serializable {
    private String time;
    private int count;

    public GrowthTrendVo(String time, int count) {
        this.time = time;
        this.count = count;
    }
    public GrowthTrendVo() {
    }

    public String getTime() {
        return time;

    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}