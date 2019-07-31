package com.tys.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/3/27 16:15
 **/
public class CompositionVo extends BaseEntity implements Serializable {

    private Integer id;

    private String name;

    private Integer safeLevel;

    private String safeLevelDescription;

    private String usedAimStr;

    private List<String> usedAim;

    private List<Integer> efficacyCodes;

    private List<String> efficacys;

    private List<Integer> skinTypeCodes;

    private List<String> skinTypes;

    /**
     * 适合敏感
     */
    private Integer fitSensitive;

    private Integer active;

    private Integer acne;

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

    public String getUsedAimStr() {
        return usedAimStr;
    }

    public void setUsedAimStr(String usedAimStr) {
        this.usedAimStr = usedAimStr;
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

    public Integer getFitSensitive() {
        return fitSensitive;
    }

    public void setFitSensitive(Integer fitSensitive) {
        this.fitSensitive = fitSensitive;
    }

    public void setSkinTypes(List<String> skinTypes) {
        this.skinTypes = skinTypes;
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
