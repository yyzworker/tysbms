package com.tys.entity.vo;

import java.io.Serializable;

/**
 * 腾讯地图接口返回值类
 * @Author haoxu
 * @Date 2019/3/8 10:42
 **/
public class LocationInfo implements Serializable {

    private String status;

    private String message;

    private Location location;

    private String address;

    private FormattedAddresses formatted_addresses;

    private AddressComponent address_component;

    private AdInfo ad_info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public FormattedAddresses getFormatted_addresses() {
        return formatted_addresses;
    }

    public void setFormatted_addresses(FormattedAddresses formatted_addresses) {
        this.formatted_addresses = formatted_addresses;
    }

    public AddressComponent getAddress_component() {
        return address_component;
    }

    public void setAddress_component(AddressComponent address_component) {
        this.address_component = address_component;
    }

    public AdInfo getAd_info() {
        return ad_info;
    }

    public void setAd_info(AdInfo ad_info) {
        this.ad_info = ad_info;
    }


}
