package com.tys.entity.vo;

import com.tys.entity.es.Composition;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/3/29 17:19
 **/
public class CommodityVo extends BaseEntity implements Serializable {

    private Integer id;

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
    private Integer typeCode;

    /**
     * 商品类型名称
     */
    private String type;

    /**
     * 适用肤质 0通用，1男，2女
     */
    private Integer suitableSexCode;

    /**
     * 安全星级
     */
    private Double safeGrade;

    /**
     * 成分
     */
    private List<Composition> compositions;

    /**
     * 适用肤质code
     */
    private List<Integer> suitableSkinCodes;

    /**
     * 适用肤质
     */
    private List<String> suitableSkins;

    /**
     * 适合敏感
     */
    private Integer fitSensitive;

    /**
     * 功效code
     */
    private List<Integer> efficacyCodes;

    /**
     * 功效
     */
    private List<String> efficacys;

    /**
     * 香精成分数
     */
    private Integer essenceCount;

    /**
     * 防腐剂成分数
     */
    private Integer antisepticCount;

    /**
     * 孕妇慎用成分数
     */
    private Integer pregnantCautionCount;

    /**
     * 风险成分数
     */
    private Integer riskCount;

    /**
     * 致痘风险数
     */
    private Integer acneCount;

    /**
     * 图片
     */
    private String imgUrl;

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

    public Integer getSuitableSexCode() {
        return suitableSexCode;
    }

    public void setSuitableSexCode(Integer suitableSexCode) {
        this.suitableSexCode = suitableSexCode;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getSafeGrade() {
        return safeGrade;
    }

    public void setSafeGrade(Double safeGrade) {
        this.safeGrade = safeGrade;
    }

    public List<Composition> getCompositions() {
        return compositions;
    }

    public void setCompositions(List<Composition> compositions) {
        this.compositions = compositions;
    }

    public List<Integer> getSuitableSkinCodes() {
        return suitableSkinCodes;
    }

    public void setSuitableSkinCodes(List<Integer> suitableSkinCodes) {
        this.suitableSkinCodes = suitableSkinCodes;
    }

    public List<String> getSuitableSkins() {
        return suitableSkins;
    }

    public void setSuitableSkins(List<String> suitableSkins) {
        this.suitableSkins = suitableSkins;
    }

    public Integer getFitSensitive() {
        return fitSensitive;
    }

    public void setFitSensitive(Integer fitSensitive) {
        this.fitSensitive = fitSensitive;
    }

    public List<Integer> getEfficacyCodes() {
        return efficacyCodes;
    }

    public void setEfficacyCodes(List<Integer> efficacyCodes) {
        this.efficacyCodes = efficacyCodes;
    }

    public List<String> getEfficacys() {
        return efficacys;
    }

    public void setEfficacys(List<String> efficacys) {
        this.efficacys = efficacys;
    }

    public Integer getEssenceCount() {
        return essenceCount;
    }

    public void setEssenceCount(Integer essenceCount) {
        this.essenceCount = essenceCount;
    }

    public Integer getAntisepticCount() {
        return antisepticCount;
    }

    public void setAntisepticCount(Integer antisepticCount) {
        this.antisepticCount = antisepticCount;
    }

    public Integer getPregnantCautionCount() {
        return pregnantCautionCount;
    }

    public void setPregnantCautionCount(Integer pregnantCautionCount) {
        this.pregnantCautionCount = pregnantCautionCount;
    }

    public Integer getRiskCount() {
        return riskCount;
    }

    public void setRiskCount(Integer riskCount) {
        this.riskCount = riskCount;
    }

    public Integer getAcneCount() {
        return acneCount;
    }

    public void setAcneCount(Integer acneCount) {
        this.acneCount = acneCount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
