package com.tys.service;

import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.ArticleVo;


public interface ArticleService {

    public PageInfo<ArticleVo> queryArticle(Integer pageNum, Integer pageSize,String orderBy);

    public ArticleVo selectArticleVoById(Integer id);
}
