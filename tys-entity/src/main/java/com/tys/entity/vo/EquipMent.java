package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@ApiModel(description = "设备信息")
public class EquipMent extends BaseEntity implements Serializable {
    @ApiModelProperty(value = "设备主键",required = true,example = "0")
    private Integer id;

    @ApiModelProperty(value = "设备地址",required = false)
    private String emAddress;

    @ApiModelProperty(value = "设备经度",required = false)
    private String emLongitude;

    @ApiModelProperty(value = "设备维度",required = false)
    private String emLatitude;

    @ApiModelProperty(value = "设备备注",required = false)
    private String emInfo;

    @ApiModelProperty(value = "设备imie码",required = false)
    private String emImie;

    @ApiModelProperty(value = "iccid",required = false)
    private String emIccid;

    @ApiModelProperty(value = "出厂编号",required = false)
    private String emNumber;

    @ApiModelProperty(value = "场景地址",required = false)
    private String placeAddress;

    @ApiModelProperty(value = "场景备注",required = false)
    private String placeInfo;

    @ApiModelProperty(value= "最后使用时间", required = false)
    private Timestamp emLasttime;

    @ApiModelProperty(value = "设备状态", required = false)
    private  Byte emStatus;

    @ApiModelProperty(value = "设备状态", required = false)
    private  Byte emHardware;

    @ApiModelProperty(value = "设备名称", required = false)
    private String emName;

    @ApiModelProperty(value = "友盟推送id", required = false)
    private String emUpushId;

    @ApiModelProperty(value = "所属省份", required = false)
    private String emProvince;

    @ApiModelProperty(value = "所属城市", required = false)
    private String emCity;

    @ApiModelProperty(value = "程序版本号", required = false)
    private String emVersion;

    @ApiModelProperty(value = "设备照片", required = false)
    private String emPhoto;

    private String advertise;

    private Byte advertiseType;

    private String emLasttimeStart;

    private String emLasttimeEnd;

    //距离坐标点的距离，单位米
    private String distanceVal;

    private Integer updateType;

    private List<Integer> emIds;

    public List<Integer> getEmIds() {
        return emIds;
    }

    public void setEmIds(List<Integer> emIds) {
        this.emIds = emIds;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }

    public String getEmLasttimeStart() {
        return emLasttimeStart;
    }

    public void setEmLasttimeStart(String emLasttimeStart) {
        this.emLasttimeStart = emLasttimeStart;
    }

    public String getEmLasttimeEnd() {
        return emLasttimeEnd;
    }

    public void setEmLasttimeEnd(String emLasttimeEnd) {
        this.emLasttimeEnd = emLasttimeEnd;
    }

    public String getEmUpushId() {
        return emUpushId;
    }

    public void setEmUpushId(String emUpushId) {
        this.emUpushId = emUpushId;
    }

    public String getEmProvince() {
        return emProvince;
    }

    public void setEmProvince(String emProvince) {
        this.emProvince = emProvince;
    }

    public String getEmCity() {
        return emCity;
    }

    public void setEmCity(String emCity) {
        this.emCity = emCity;
    }

    public String getEmName() {
        return emName;
    }

    public void setEmName(String emName) {
        this.emName = emName;
    }

    public Timestamp getEmLasttime() {
        return emLasttime;
    }

    public void setEmLasttime(Timestamp emLasttime) {
        this.emLasttime = emLasttime;
    }

    public Byte getEmStatus() {
        return emStatus;
    }

    public void setEmStatus(Byte emStatus) {
        this.emStatus = emStatus;
    }

    public Byte getEmHardware() {
        return emHardware;
    }

    public void setEmHardware(Byte emHardware) {
        this.emHardware = emHardware;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmAddress() {
        return emAddress;
    }

    public void setEmAddress(String emAddress) {
        this.emAddress = emAddress == null ? null : emAddress.trim();
    }

    public String getEmLongitude() {
        return emLongitude;
    }

    public void setEmLongitude(String emLongitude) {
        this.emLongitude = emLongitude == null ? null : emLongitude.trim();
    }

    public String getEmLatitude() {
        return emLatitude;
    }

    public void setEmLatitude(String emLatitude) {
        this.emLatitude = emLatitude == null ? null : emLatitude.trim();
    }

    public String getEmInfo() {
        return emInfo;
    }

    public void setEmInfo(String emInfo) {
        this.emInfo = emInfo == null ? null : emInfo.trim();
    }

    public String getEmImie() {
        return emImie;
    }

    public void setEmImie(String emImie) {
        this.emImie = emImie == null ? null : emImie.trim();
    }

    public String getEmVersion() {
        return emVersion;
    }

    public void setEmVersion(String emVersion) {
        this.emVersion = emVersion;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress == null ? null : placeAddress.trim();
    }

    public String getPlaceInfo() {
        return placeInfo;
    }

    public void setPlaceInfo(String placeInfo) {
        this.placeInfo = placeInfo == null ? null : placeInfo.trim();
    }

    public String getDistanceVal() {
        return distanceVal;
    }

    public void setDistanceVal(String distanceVal) {
        this.distanceVal = distanceVal;
    }

    public String getEmPhoto() { return emPhoto; }

    public void setEmPhoto(String emPhoto) { this.emPhoto = emPhoto;}

    public String getAdvertise() { return advertise; }

    public void setAdvertise(String advertise) { this.advertise = advertise; }

    public Byte getAdvertiseType() { return advertiseType; }

    public void setAdvertiseType(Byte advertiseType) { this.advertiseType = advertiseType;  }

    public String getEmIccid() {
        return emIccid;
    }

    public void setEmIccid(String emIccid) {
        this.emIccid = emIccid;
    }

    public String getEmNumber() {
        return emNumber;
    }

    public void setEmNumber(String emNumber) {
        this.emNumber = emNumber;
    }
}