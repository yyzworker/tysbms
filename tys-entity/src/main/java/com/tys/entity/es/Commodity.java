package com.tys.entity.es;

import com.tys.entity.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/3/29 17:19
 **/
@Document(indexName = "index_commodity",type = "commodity")
public class Commodity extends Indexable {
    /**
     * 商品名称
     */
    @MultiField(
        mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
        otherFields ={ @InnerField(suffix ="standard",type = FieldType.Text,analyzer = "standard")}
    )
    private String name;

    /**
     * 英文名
     */
    @Field(type = FieldType.Keyword)
    private String enName;

    /**
     * 别名
     */
    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields ={ @InnerField(suffix ="standard",type = FieldType.Text,analyzer = "standard")}
    )
    private String alias;

    /**
     * 商品类型code
     */
    @Field(type = FieldType.Integer)
    private Integer typeCode;

    /**
     * 商品类型名称
     */
    @Field(type = FieldType.Keyword)
    private String type;

    /**
     * 适用肤质 0通用，1男，2女
     */
    @Field(type = FieldType.Integer)
    private Integer suitableSexCode;

    /**
     * 安全星级
     */
    @Field(type = FieldType.Double)
    private Double safeGrade;

    /**
     * 成分
     */
    @Field(type = FieldType.Nested)
    private List<Composition> compositions;

    /**
     * 适用肤质code
     */
    @Field(type = FieldType.Integer)
    private List<Integer> suitableSkinCodes;

    /**
     * 适用肤质
     */
    @Field(type = FieldType.Keyword)
    private List<String> suitableSkins;

    /**
     * 适合敏感
     */
    @Field(type = FieldType.Integer)
    private Integer fitSensitive;

    /**
     * 功效code
     */
    @Field(type = FieldType.Integer)
    private List<Integer> efficacyCodes;

    /**
     * 功效
     */
    @Field(type = FieldType.Keyword)
    private List<String> efficacys;

    /**
     * 香精成分数
     */
    @Field(type = FieldType.Integer)
    private Integer essenceCount;

    /**
     * 防腐剂成分数
     */
    @Field(type = FieldType.Integer)
    private Integer antisepticCount;

    /**
     * 孕妇慎用成分数
     */
    @Field(type = FieldType.Integer)
    private Integer pregnantCautionCount;

    /**
     * 风险成分数
     */
    @Field(type = FieldType.Integer)
    private Integer riskCount;

    /**
     * 致痘风险数
     */
    @Field(type = FieldType.Integer)
    private Integer acneCount;

    /**
     * 图片
     */
    @Field(type = FieldType.Keyword)
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
