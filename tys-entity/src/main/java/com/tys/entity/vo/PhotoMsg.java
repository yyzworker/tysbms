package com.tys.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/3/27 13:35
 **/
public class PhotoMsg implements Serializable {

    private Integer userId;

    private Integer emId;

    private Byte uploadType;

    private List<Photo> photos;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEmId() {
        return emId;
    }

    public void setEmId(Integer emId) {
        this.emId = emId;
    }

    public Byte getUploadType() {
        return uploadType;
    }

    public void setUploadType(Byte uploadType) {
        this.uploadType = uploadType;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }


}
