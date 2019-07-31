package com.tys.entity.vo;

import java.io.Serializable;

/**
 * 行政区划类
 * @Author haoxu
 * @Date 2019/3/8 13:26
 **/
public class AdInfo implements Serializable {
    String nation_code;
    String adcode;
    String city_code;
    String name;
    Location location;
    String nation;
    String province;
    String city;
    String district;
    public String getNation_code() {
        return nation_code;
    }

    public void setNation_code(String nation_code) {
        this.nation_code = nation_code;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}