package com.tys.service;
import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.ArticleVo;

import java.util.List;

public interface ArticleService {
    int insertArticle(ArticleVo record);

    PageInfo<ArticleVo> queryArticle(ArticleVo articleVo);

    int updateArticle(ArticleVo articleVo);

    ArticleVo getArticleById(Integer id);

    int deleteArticleById(Integer id);

}
