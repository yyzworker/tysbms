package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;

@ApiModel(description = "检测报告")
public class DetectRecord extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键", required = true)
    private Integer id;

    @ApiModelProperty(value = "会员id", required = true)
    private Integer memberId;

    @ApiModelProperty(value = "设备id", required = true)
    private Integer equipmentId;

    @ApiModelProperty(value = "检测时间", required = true)
    private Timestamp detectTime;

    @ApiModelProperty(value = "检测地点", required = true)
    private String detectLocation;

    @ApiModelProperty(value = "报告主体data格式内容", required = true)
    private String data;

    @ApiModelProperty(value = "所属省份", required = false)
    private String detectProvince;

    @ApiModelProperty(value = "所属城市", required = false)
    private String detectCity;

    @ApiModelProperty(value = "图片地址", required = false)
    private String photoPath;

    @ApiModelProperty(value = "计算结果",required = false)
    private String result;

    @ApiModelProperty(value = "3d图片路径",required = false)
    private String threePath;

    @ApiModelProperty(value = "上传方式", required = true)
    private Byte uploadType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Timestamp getDetectTime() {
        return detectTime;
    }

    public void setDetectTime(Timestamp detectTime) {
        this.detectTime = detectTime;
    }

    public String getDetectLocation() {
        return detectLocation;
    }

    public void setDetectLocation(String detectLocation) {
        this.detectLocation = detectLocation;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDetectProvince() {
        return detectProvince;
    }

    public void setDetectProvince(String detectProvince) {
        this.detectProvince = detectProvince;
    }

    public String getDetectCity() {
        return detectCity;
    }

    public void setDetectCity(String detectCity) {
        this.detectCity = detectCity;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getThreePath() {
        return threePath;
    }

    public void setThreePath(String threePath) {
        this.threePath = threePath;
    }

    public Byte getUploadType() {
        return uploadType;
    }

    public void setUploadType(Byte uploadType) {
        this.uploadType = uploadType;
    }
}