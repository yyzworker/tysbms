package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @author ：王志浩
 * @date ：Created in 2019-5-7 09:31
 */
@ApiModel(description = "主页")
public class HomeVo implements Serializable {
    private String memberTotal;
    private String memberWoman;
    private String memberMan;
    private String memberNewAdd;

    private String detectTotal;
    private String detectWoman;
    private String detectMan;
    private String detectNewAdd;

    private String equipmentTotal;
    private String equipmentNormal;
    private String equipmentAbNormal;
    private String equipmentShutDown;

    public String getMemberTotal() {
        return memberTotal;
    }

    public void setMemberTotal(String memberTotal) {
        this.memberTotal = memberTotal;
    }

    public String getMemberWoman() {
        return memberWoman;
    }

    public void setMemberWoman(String memberWoman) {
        this.memberWoman = memberWoman;
    }

    public String getMemberMan() {
        return memberMan;
    }

    public void setMemberMan(String memberMan) {
        this.memberMan = memberMan;
    }

    public String getMemberNewAdd() {
        return memberNewAdd;
    }

    public void setMemberNewAdd(String memberNewAdd) {
        this.memberNewAdd = memberNewAdd;
    }

    public String getDetectTotal() {
        return detectTotal;
    }

    public void setDetectTotal(String detectTotal) {
        this.detectTotal = detectTotal;
    }

    public String getDetectWoman() {
        return detectWoman;
    }

    public void setDetectWoman(String detectWoman) {
        this.detectWoman = detectWoman;
    }

    public String getDetectMan() {
        return detectMan;
    }

    public void setDetectMan(String detectMan) {
        this.detectMan = detectMan;
    }

    public String getDetectNewAdd() {
        return detectNewAdd;
    }

    public void setDetectNewAdd(String detectNewAdd) {
        this.detectNewAdd = detectNewAdd;
    }

    public String getEquipmentTotal() {
        return equipmentTotal;
    }

    public void setEquipmentTotal(String equipmentTotal) {
        this.equipmentTotal = equipmentTotal;
    }

    public String getEquipmentNormal() {
        return equipmentNormal;
    }

    public void setEquipmentNormal(String equipmentNormal) {
        this.equipmentNormal = equipmentNormal;
    }

    public String getEquipmentAbNormal() {
        return equipmentAbNormal;
    }

    public void setEquipmentAbNormal(String equipmentAbNormal) {
        this.equipmentAbNormal = equipmentAbNormal;
    }

    public String getEquipmentShutDown() {
        return equipmentShutDown;
    }

    public void setEquipmentShutDown(String equipmentShutDown) {
        this.equipmentShutDown = equipmentShutDown;
    }
}