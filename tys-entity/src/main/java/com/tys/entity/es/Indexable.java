package com.tys.entity.es;

import com.tys.entity.annotation.DateFormat;
import com.tys.entity.annotation.Field;
import com.tys.entity.annotation.FieldType;
import com.tys.entity.annotation.Id;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author haoxu
 * @Date 2019/5/23 9:30
 **/
public class Indexable implements Serializable {

    @Id
    private Integer id;

    @Field
    private String version;

    /**
     * 状态 0无效 1有效
     */
    @Field(type = FieldType.Integer)
    private Integer status;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||epoch_millis")
    private Timestamp createTime;

    /**
     * 创建人
     */
    @Field(type = FieldType.Integer)
    private Integer createUserId;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||epoch_millis")
    private Timestamp updateTime;

    /**
     * 更新人
     */
    @Field(type = FieldType.Integer)
    private Integer updateUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }
}
