package com.tys.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author haoxu
 * @Date 2019/3/15 13:45
 **/
public class DetectMemberVo extends BaseEntity implements Serializable {
    private Integer id;

    private Integer memberId;

    private Integer equipmentId;

    private Date detectTime;

    private String detectLocation;

    private String json;

    private Byte comprehensiveScore;

    private String detectProvince;

    private String detectCity;

    private Byte waterOilBalance;

    private Byte cleanSkin;

    private Byte colorSpotIndex;

    private Byte youngDegree;

    private Byte smoothness;

    private String photoPath;

    private String result;

    private String name;

    private String phone;

    private Byte sex;

    private Timestamp birthdate;

    private String jobTag;

    private Timestamp registrationDate;

    private Integer detectionsNumber;

    private Timestamp detectionsLasttime;

    private String avatarUrl;

    private Short age;

    private Short height;

    private Short weight;

    private String hobbyTag;

    private String inviter;

    private String emName;

    private String detectTimeStart;

    private String detectTimeEnd;

    private String birthdateStart;

    private String birthdateEnd;

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }


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

    public Date getDetectTime() {
        return detectTime;
    }

    public void setDetectTime(Date detectTime) {
        this.detectTime = detectTime;
    }

    public String getDetectLocation() {
        return detectLocation;
    }

    public void setDetectLocation(String detectLocation) {
        this.detectLocation = detectLocation;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Byte getComprehensiveScore() {
        return comprehensiveScore;
    }

    public void setComprehensiveScore(Byte comprehensiveScore) {
        this.comprehensiveScore = comprehensiveScore;
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

    public Byte getWaterOilBalance() {
        return waterOilBalance;
    }

    public void setWaterOilBalance(Byte waterOilBalance) {
        this.waterOilBalance = waterOilBalance;
    }

    public Byte getCleanSkin() {
        return cleanSkin;
    }

    public void setCleanSkin(Byte cleanSkin) {
        this.cleanSkin = cleanSkin;
    }

    public Byte getColorSpotIndex() {
        return colorSpotIndex;
    }

    public void setColorSpotIndex(Byte colorSpotIndex) {
        this.colorSpotIndex = colorSpotIndex;
    }

    public Byte getYoungDegree() {
        return youngDegree;
    }

    public void setYoungDegree(Byte youngDegree) {
        this.youngDegree = youngDegree;
    }

    public Byte getSmoothness() {
        return smoothness;
    }

    public void setSmoothness(Byte smoothness) {
        this.smoothness = smoothness;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Timestamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;
    }

    public String getJobTag() {
        return jobTag;
    }

    public void setJobTag(String jobTag) {
        this.jobTag = jobTag;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getDetectionsNumber() {
        return detectionsNumber;
    }

    public void setDetectionsNumber(Integer detectionsNumber) {
        this.detectionsNumber = detectionsNumber;
    }

    public Timestamp getDetectionsLasttime() {
        return detectionsLasttime;
    }

    public void setDetectionsLasttime(Timestamp detectionsLasttime) {
        this.detectionsLasttime = detectionsLasttime;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public Short getHeight() {
        return height;
    }

    public void setHeight(Short height) {
        this.height = height;
    }

    public Short getWeight() {
        return weight;
    }

    public void setWeight(Short weight) {
        this.weight = weight;
    }

    public String getHobbyTag() {
        return hobbyTag;
    }

    public void setHobbyTag(String hobbyTag) {
        this.hobbyTag = hobbyTag;
    }

    public String getEmName() {
        return emName;
    }

    public void setEmName(String emName) {
        this.emName = emName;
    }

    public String getDetectTimeStart() {
        return detectTimeStart;
    }

    public void setDetectTimeStart(String detectTimeStart) {
        this.detectTimeStart = detectTimeStart;
    }

    public String getDetectTimeEnd() {
        return detectTimeEnd;
    }

    public void setDetectTimeEnd(String detectTimeEnd) {
        this.detectTimeEnd = detectTimeEnd;
    }

    public String getBirthdateStart() {
        return birthdateStart;
    }

    public void setBirthdateStart(String birthdateStart) {
        this.birthdateStart = birthdateStart;
    }

    public String getBirthdateEnd() {
        return birthdateEnd;
    }

    public void setBirthdateEnd(String birthdateEnd) {
        this.birthdateEnd = birthdateEnd;
    }
}
