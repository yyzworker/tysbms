package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = " 颜值印象信息")
public class FaceValue extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "颜值印象主键",required = true)
    private Integer id;

    @ApiModelProperty(value = "性别",required = true)
    private Byte sex;

    @ApiModelProperty(value = "检测项目",required = true)
    private Byte testItems;

    @ApiModelProperty(value = "检测结果",required = true)
    private Byte testResults;

    @ApiModelProperty(value = "颜值印象",required = true)
    private String faceValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getTestItems() {
        return testItems;
    }

    public void setTestItems(Byte testItems) {
        this.testItems = testItems;
    }

    public Byte getTestResults() {
        return testResults;
    }

    public void setTestResults(Byte testResults) {
        this.testResults = testResults;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue == null ? null : faceValue.trim();
    }
}