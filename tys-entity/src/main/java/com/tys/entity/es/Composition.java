package com.tys.entity.es;

import com.tys.entity.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/3/27 16:15
 **/
@Document(indexName = "index_composition",type = "composition")
public class Composition  extends Indexable{

    /**
     * 成分名称
     */
    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields ={ @InnerField(suffix ="standard",type = FieldType.Text,analyzer = "standard")}
    )
    private String name;

    /**
     * 安全等级
     */
    @Field(type = FieldType.Integer)
    private Integer safeLevel;

    /**
     * 安全等级描述
     */
    @Field(type = FieldType.Keyword)
    private String safeLevelDescription;

    /**
     * 使用目的
     */
    @Field(type = FieldType.Keyword)
    private List<String> usedAim;

    /**
     * 功效代码
     */
    @Field(type = FieldType.Integer)
    private List<Integer> efficacyCodes;

    /**
     * 功效
     */
    @Field(type = FieldType.Keyword)
    private List<String> efficacys;

    /**
     * 适用皮肤代码
     */
    @Field(type = FieldType.Integer)
    private List<Integer> skinTypeCodes;

    /**
     * 适用皮肤
     */
    @Field(type = FieldType.Keyword)
    private List<String> skinTypes;

    /**
     * 适合敏感
     */
    @Field(type = FieldType.Integer)
    private Integer fitSensitive;

    /**
     * 活性成分
     */
    @Field(type = FieldType.Integer)
    private Integer active;

    /**
     * 致痘风险
     */
    @Field(type = FieldType.Integer)
    private Integer acne;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSafeLevel() {
        return safeLevel;
    }

    public void setSafeLevel(Integer safeLevel) {
        this.safeLevel = safeLevel;
    }

    public String getSafeLevelDescription() {
        return safeLevelDescription;
    }

    public void setSafeLevelDescription(String safeLevelDescription) {
        this.safeLevelDescription = safeLevelDescription;
    }

    public List<String> getUsedAim() {
        return usedAim;
    }

    public void setUsedAim(List<String> usedAim) {
        this.usedAim = usedAim;
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

    public List<Integer> getSkinTypeCodes() {
        return skinTypeCodes;
    }

    public void setSkinTypeCodes(List<Integer> skinTypeCodes) {
        this.skinTypeCodes = skinTypeCodes;
    }

    public List<String> getSkinTypes() {
        return skinTypes;
    }

    public void setSkinTypes(List<String> skinTypes) {
        this.skinTypes = skinTypes;
    }

    public Integer getFitSensitive() {
        return fitSensitive;
    }

    public void setFitSensitive(Integer fitSensitive) {
        this.fitSensitive = fitSensitive;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getAcne() {
        return acne;
    }

    public void setAcne(Integer acne) {
        this.acne = acne;
    }

}
