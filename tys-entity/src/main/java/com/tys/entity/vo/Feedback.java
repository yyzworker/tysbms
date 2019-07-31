package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author haoxu
 * @Date 2019/6/14 11:56
 **/
@ApiModel(description = "建议反馈")
public class Feedback extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "建议反馈主键",required = true ,example = "0")
    private Integer id;

    @ApiModelProperty(value = "用户id",required = true ,example = "0")
    private Integer memberId;

    @ApiModelProperty(value = "联系方式",required = false )
    private String contact;

    @ApiModelProperty(value = "内容",required = false )
    private String content;

    private String createTimeStart;

    private String createTimeEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

}
