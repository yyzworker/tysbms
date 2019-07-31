package com.tys.entity.vo;

import java.io.Serializable;

/**
 * 地址分解（国家，省，市，区，街道，门牌号）
 * @Author haoxu
 * @Date 2019/3/8 11:13
 **/
public class AddressComponent  implements Serializable {
    String nation;
    String province;
    String city;
    String district;
    String street;
    String street_number;

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet_number() {
        return street_number;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }
}
