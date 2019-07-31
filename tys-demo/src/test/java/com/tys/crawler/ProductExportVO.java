package com.tys.crawler;


import java.io.Serializable;

public class ProductExportVO implements Serializable {
    /**
     * 商品名称

     */
    private String name;

    /**
     * 英文名
     */
    private String enName;

    /**
     * 别名
     */
    private String alias;

    /**
     * 商品类型code
     */
    private String typeCode;

    /**
     * 商品类型名称
     */
    private String type;

    /**
     * 适用肤质 0通用，1男，2女
     */
    private String suitableSexCode;

    /**
     * 安全星级
     */
    private String safeGrade;

    /**
     * 成分
     */
    private String compositions;

    /**
     * 适用肤质code
     */
    private String suitableSkinCodes;

    /**
     * 适用肤质
     */
    private String suitableSkins;

    /**
     * 功效code
     */
    private String efficacyCodes;

    /**
     * 功效
     */
    private String efficacys;

    /**
     * 香精成分数
     */
    private String essenceCount;

    /**
     * 防腐剂成分数
     */
    private String antisepticCount;

    /**
     * 孕妇慎用成分数
     */
    private String pregnantCautionCount;

    /**
     * 风险成分数
     */
    private String riskCount;

    /**
     * 致痘风险数
     */
    private String acneCount;

    /**
     * 图片
     */
    private String imgUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSuitableSexCode() {
        return suitableSexCode;
    }

    public void setSuitableSexCode(String suitableSexCode) {
        this.suitableSexCode = suitableSexCode;
    }

    public String getSafeGrade() {
        return safeGrade;
    }

    public void setSafeGrade(String safeGrade) {
        this.safeGrade = safeGrade;
    }

    public String getCompositions() {
        return compositions;
    }

    public void setCompositions(String compositions) {
        this.compositions = compositions;
    }

    public String getSuitableSkinCodes() {
        return suitableSkinCodes;
    }

    public void setSuitableSkinCodes(String suitableSkinCodes) {
        this.suitableSkinCodes = suitableSkinCodes;
    }

    public String getSuitableSkins() {
        return suitableSkins;
    }

    public void setSuitableSkins(String suitableSkins) {
        this.suitableSkins = suitableSkins;
    }

    public String getEfficacyCodes() {
        return efficacyCodes;
    }
    public void setEfficacyCodes(String efficacyCodes) {
        this.efficacyCodes = efficacyCodes;
    }

    public String getEfficacys() {
        return efficacys;
    }

    public void setEfficacys(String efficacys) {
        this.efficacys = efficacys;
    }

    public String getEssenceCount() {
        return essenceCount;
    }

    public void setEssenceCount(String essenceCount) {
        this.essenceCount = essenceCount;
    }

    public String getAntisepticCount() {
        return antisepticCount;
    }

    public void setAntisepticCount(String antisepticCount) {
        this.antisepticCount = antisepticCount;
    }

    public String getPregnantCautionCount() {
        return pregnantCautionCount;
    }

    public void setPregnantCautionCount(String pregnantCautionCount) {
        this.pregnantCautionCount = pregnantCautionCount;
    }

    public String getRiskCount() {
        return riskCount;
    }

    public void setRiskCount(String riskCount) {
        this.riskCount = riskCount;
    }

    public String getAcneCount() {
        return acneCount;
    }

    public void setAcneCount(String acneCount) {
        this.acneCount = acneCount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
}