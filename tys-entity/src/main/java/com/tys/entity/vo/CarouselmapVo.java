package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author ：王志浩
 * @date ：Created in 2019-4-28 09:31
 */
@ApiModel(description = "轮播图设置")
public class CarouselmapVo extends BaseEntity implements Serializable {
	@ApiModelProperty(value = "主键", required = false)
    private String id;
    
    @ApiModelProperty(value = "名称", required = false)
    private String name;

    @ApiModelProperty(value = "图片路径", required = false)
    private String imgUrl;

    @ApiModelProperty(value = "使用类别", required = false)
    private Byte useType;

    @ApiModelProperty(value = "跳转类别", required = false)
    private Byte jumpType;

    @ApiModelProperty(value = "跳转url", required = false)
    private String jumpUrl;

    @ApiModelProperty(value = "是否生效", required = false)
    private Byte effective;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Byte getUseType() {
        return useType;
    }

    public void setUseType(Byte useType) {
        this.useType = useType;
    }

    public Byte getJumpType() {
        return jumpType;
    }

    public void setJumpType(Byte jumpType) {
        this.jumpType = jumpType;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public Byte getEffective() {
        return effective;
    }

    public void setEffective(Byte effective) {
        this.effective = effective;
    }





}