package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;

@ApiModel(description = "会员信息")
public class MemberInfo extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "会员信息主键",required = true)
    private Integer id;

    @ApiModelProperty(value = "openid",required = true)
    private String openId;

    @ApiModelProperty(value = "unionId",required = true)
    private String unionId;

    @ApiModelProperty(value = "昵称",required = true)
    private String name;

    @ApiModelProperty(value = "手机号",required = true)
    private String phone;

    @ApiModelProperty(value = "性别",required = false)
    private Byte sex;

    @ApiModelProperty(value = "出生日期",required = false)
    private Timestamp birthdate;

    @ApiModelProperty(value = "职业标签",required = false)
    private String jobTag;

    @ApiModelProperty(value = "注册日期",required = true)
    private Timestamp registrationDate;

    @ApiModelProperty(value = "用户检测次数",required = true)
    private Integer detectionsNumber;

    @ApiModelProperty(value = "静默上传次数",required = true)
    private Integer silentNumber;

    @ApiModelProperty(value = "检验最后时间",required = false)
    private Timestamp detectionsLasttime;

    @ApiModelProperty(value = "头像地址",required = true)
    private String avatarUrl;

    @ApiModelProperty(value = "年龄",required = false)
    private Short age;

    @ApiModelProperty(value = "身高",required = false)
    private Short height;

    @ApiModelProperty(value = "体重",required = false)
    private Short weight;

    @ApiModelProperty(value = "胸围",required = false)
    private Short bust;

    @ApiModelProperty(value = "腰围",required = false)
    private Short waistline;

    @ApiModelProperty(value = "臀围",required = false)
    private Short buttline;

    @ApiModelProperty(value = "爱好标签",required = false)
    private String hobbyTag;

    @ApiModelProperty(value = "邀请人",required = false)
    private Integer inviter;

    private String registrationDateStart;

    private String registrationDateEnd;

    private String detectionsLasttimeStart;

    private String detectionsLasttimeEnd;

    private Integer detectionsNumberStart;

    private Integer detectionsNumberEnd;

    private Short ageStart;

    private Short ageEnd;

    private String birthdateStart;
    //总连胜次数
    @ApiModelProperty(value = "最大连胜次数",required = false)
    private Integer topWinTime ;
    @ApiModelProperty(value = "近期连胜次数",required = false)
    //近期连胜次数
    private Integer nearWinTime ;
    //总胜次数
    @ApiModelProperty(value = "总胜次数",required = false)
    private Integer sumWinTime;
    //总失败次数
    @ApiModelProperty(value = "总失败次数",required = false)
    private Integer sumFailTime;

    public Integer getExpSum() {
        return expSum;
    }

    public Integer getTopWinTime() {
        return topWinTime;
    }

    public void setTopWinTime(Integer topWinTime) {
        this.topWinTime = topWinTime;
    }

    public Integer getNearWinTime() {
        return nearWinTime;
    }

    public void setNearWinTime(Integer nearWinTime) {
        this.nearWinTime = nearWinTime;
    }

    public Integer getSumWinTime() {
        return sumWinTime;
    }

    public void setSumWinTime(Integer sumWinTime) {
        this.sumWinTime = sumWinTime;
    }

    public Integer getSumFailTime() {
        return sumFailTime;
    }

    public void setSumFailTime(Integer sumFailTime) {
        this.sumFailTime = sumFailTime;
    }

    public void setExpSum(Integer expSum) {
        this.expSum = expSum;
    }

    private String birthdateEnd;

    @ApiModelProperty(value = "用户积分",required = false)
    private Integer expSum;

    public String getRegistrationDateStart() {
        return registrationDateStart;
    }

    public void setRegistrationDateStart(String registrationDateStart) {
        this.registrationDateStart = registrationDateStart;
    }

    public String getRegistrationDateEnd() {
        return registrationDateEnd;
    }

    public void setRegistrationDateEnd(String registrationDateEnd) {
        this.registrationDateEnd = registrationDateEnd;
    }

    public String getDetectionsLasttimeStart() {
        return detectionsLasttimeStart;
    }

    public void setDetectionsLasttimeStart(String detectionsLasttimeStart) {
        this.detectionsLasttimeStart = detectionsLasttimeStart;
    }

    public String getDetectionsLasttimeEnd() {
        return detectionsLasttimeEnd;
    }

    public void setDetectionsLasttimeEnd(String detectionsLasttimeEnd) {
        this.detectionsLasttimeEnd = detectionsLasttimeEnd;
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

    public Integer getInviter() {
        return inviter;
    }

    public void setInviter(Integer inviter) {
        this.inviter = inviter;
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

    public Short getBust() {
        return bust;
    }

    public void setBust(Short bust) {
        this.bust = bust;
    }

    public Short getWaistline() {
        return waistline;
    }

    public void setWaistline(Short waistline) {
        this.waistline = waistline;
    }

    public Short getButtline() {
        return buttline;
    }

    public void setButtline(Short buttline) {
        this.buttline = buttline;
    }

    public String getHobbyTag() {
        return hobbyTag;
    }

    public void setHobbyTag(String hobbyTag) {
        this.hobbyTag = hobbyTag;
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public Integer getDetectionsNumberStart() {
        return detectionsNumberStart;
    }

    public void setDetectionsNumberStart(Integer detectionsNumberStart) {
        this.detectionsNumberStart = detectionsNumberStart;
    }

    public Integer getSilentNumber() {
        return silentNumber;
    }

    public void setSilentNumber(Integer silentNumber) {
        this.silentNumber = silentNumber;
    }

    public Integer getDetectionsNumberEnd() {
        return detectionsNumberEnd;
    }

    public void setDetectionsNumberEnd(Integer detectionsNumberEnd) {
        this.detectionsNumberEnd = detectionsNumberEnd;
    }

    public Short getAgeStart() {
        return ageStart;
    }

    public void setAgeStart(Short ageStart) {
        this.ageStart = ageStart;
    }

    public Short getAgeEnd() {
        return ageEnd;
    }

    public void setAgeEnd(Short ageEnd) {
        this.ageEnd = ageEnd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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
        this.jobTag = jobTag == null ? null : jobTag.trim();
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
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}