package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author ：王志浩
 * @date ：Created in 2019-4-8 14:20
 */
@ApiModel(description = "我的妆品信息")
public class MyCosmeticsVo extends BaseEntity implements Serializable {
    @ApiModelProperty(value = "我的妆品信息主键",required = false)
    private Integer mycosmeticsId;
    @ApiModelProperty(value = "用户主键",required = true)
    private Integer memberId;
    @ApiModelProperty(value = "妆品ES主键",required = true)
    private String cosmeticId;
    @ApiModelProperty(value = "妆品类型",required = true)
    private Byte typeCode;
    public Integer getMycosmeticsId() {
        return mycosmeticsId;
    }

    public void setMycosmeticsId(Integer mycosmeticsId) {
        this.mycosmeticsId = mycosmeticsId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getCosmeticId() {
        return cosmeticId;
    }

    public void setCosmeticId(String cosmeticId) {
        this.cosmeticId = cosmeticId;
    }

    public Byte getTypeCode() { return typeCode; }

    public void setTypeCode(Byte typeCode) { this.typeCode = typeCode; }
}
