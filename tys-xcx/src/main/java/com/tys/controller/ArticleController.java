package com.tys.controller;

import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.ArticleVo;
import com.tys.service.ArticleService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author haoxu
 * @Date 2019/7/4 16:21
 **/
//@RestController
@RequestMapping(value = "/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public ReturnMessage queryArticle(Integer pageNum,Integer pageSize,String orderBy){
        PageInfo<ArticleVo> pageInfo = articleService.queryArticle(pageNum,pageSize,orderBy);
        return new ReturnMessage("success",pageInfo);
    }

    @GetMapping("/{id}")
    public ReturnMessage selectArticleById(@PathVariable Integer id){
        return new ReturnMessage("success",articleService.selectArticleVoById(id));
    }

}
