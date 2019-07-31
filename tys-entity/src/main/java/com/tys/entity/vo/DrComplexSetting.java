package com.tys.entity.vo;

import java.io.Serializable;

public class DrComplexSetting extends BaseEntity implements Serializable {
    private Integer id;

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