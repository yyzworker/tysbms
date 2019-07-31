package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "标签信息")
public class TagInfo extends BaseEntity implements Serializable {
    @ApiModelProperty(value = "标签主键", required = true)
    private Integer id;

    @ApiModelProperty(value = "标签分类", required = true)
    private Byte tagType;

    @ApiModelProperty(value = "标签状态", required = true)
    private Byte tagStatus;

    @ApiModelProperty(value = "标签内容", required = false)
    private String tagContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getTagType() {
        return tagType;
    }

    public void setTagType(Byte tagType) {
        this.tagType = tagType;
    }

    public Byte getTagStatus() {
        return tagStatus;
    }

    public void setTagStatus(Byte tagStatus) {
        this.tagStatus = tagStatus;
    }

    public String getTagContent() {
        return tagContent;
    }

    public void setTagContent(String tagContent) {
        this.tagContent = tagContent == null ? null : tagContent.trim();
    }
}