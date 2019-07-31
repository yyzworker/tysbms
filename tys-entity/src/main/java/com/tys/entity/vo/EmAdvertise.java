package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/7/12 14:29
 **/
@ApiModel(description = "设备广告关系信息")
public class EmAdvertise  extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键",required = true,example = "0")
    private Integer id;

    @ApiModelProperty(value = "设备主键",required = false)
    private Integer emId;

    @ApiModelProperty(value = "设备主键",required = false)
    private String advertise;

    @ApiModelProperty(value = "广告类型",required = false)
    private Byte advertiseType;

    private List<Integer> emIds;

    public List<Integer> getEmIds() {
        return emIds;
    }

    public void setEmIds(List<Integer> emIds) {
        this.emIds = emIds;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmId() {
        return emId;
    }

    public void setEmId(Integer emId) {
        this.emId = emId;
    }

    public String getAdvertise() {
        return advertise;
    }

    public void setAdvertise(String advertise) {
        this.advertise = advertise;
    }

    public Byte getAdvertiseType() {
        return advertiseType;
    }

    public void setAdvertiseType(Byte advertiseType) {
        this.advertiseType = advertiseType;
    }
}
