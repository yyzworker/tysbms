package com.tys.entity.wx;

import com.tys.entity.vo.Watermark;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-3-11 13:31
 */
public class WXPhoneInfo {
    private String phoneNumber;
    private String purePhoneNumber;
    private String countryCode;
    private Watermark watermark;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Watermark getWatermark() {
        return watermark;
    }

    public void setWatermark(Watermark watermark) {
        this.watermark = watermark;
    }
}
