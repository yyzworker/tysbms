package com.tys.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

public class Notice {
    @ApiModelProperty(value = "通知ID")
    private Integer id;
    @ApiModelProperty(value = "通知标题")
    private String title;
    @ApiModelProperty(value = "通知内容")
    private String content;
    @ApiModelProperty(value = "创建时间")
    private Timestamp createDate;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}
