package com.tys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author 王志浩
 * @Date 2019/7/1 11:56
 **/
@ApiModel(description = "文章管理")
public class ArticleVo extends BaseEntity implements Serializable {
    @ApiModelProperty(value = "主键",required = true ,example = "0")
    private Integer id;

    @ApiModelProperty(value = "文章标题",required = false )
    private String articleTitle;

    @ApiModelProperty(value = "文章内容",required = false )
    private String articleContent;

    @ApiModelProperty(value = "文章类别",required = false )
    private String articleType;

    @ApiModelProperty(value = "文章网址",required = false )
    private String articleUrl;

    @ApiModelProperty(value = "图片路径",required = false )
    private String imgUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
