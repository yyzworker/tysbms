package com.tys.entity.vo;

import java.io.Serializable;

public class DrProblemSetting extends BaseEntity implements Serializable {
    private Integer id;

    private Byte analysisProject;

    private Byte level;

    private Double minValue;

    private Double maxValue;

    private Byte skinType;

    private Byte sensitiveType;

    private Byte descriptionType;

    private String descriptionContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getAnalysisProject() {
        return analysisProject;
    }

    public void setAnalysisProject(Byte analysisProject) {
        this.analysisProject = analysisProject;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Byte getSkinType() {
        return skinType;
    }

    public void setSkinType(Byte skinType) {
        this.skinType = skinType;
    }

    public Byte getSensitiveType() {
        return sensitiveType;
    }

    public void setSensitiveType(Byte sensitiveType) {
        this.sensitiveType = sensitiveType;
    }

    public Byte getDescriptionType() {
        return descriptionType;
    }

    public void setDescriptionType(Byte descriptionType) {
        this.descriptionType = descriptionType;
    }

    public String getDescriptionContent() {
        return descriptionContent;
    }

    public void setDescriptionContent(String descriptionContent) {
        this.descriptionContent = descriptionContent == null ? null : descriptionContent.trim();
    }
}