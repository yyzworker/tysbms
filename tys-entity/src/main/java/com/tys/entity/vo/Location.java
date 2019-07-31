package com.tys.entity.vo;

import java.io.Serializable;

/**
 * 经纬度
 * @Author haoxu
 * @Date 2019/3/8 11:13
 **/
public class Location implements Serializable {
    String lat;
    String lng;
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
