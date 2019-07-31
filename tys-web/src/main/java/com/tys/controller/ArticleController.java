package com.tys.controller;

import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.ArticleVo;
import com.tys.entity.vo.Feedback;
import com.tys.service.ArticleService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author wangzhihao
 * @Date 2019/7/1 14:05
 **/
@RestController
@RequestMapping(value = "/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ReturnMessage insertArticle(@RequestBody ArticleVo articleVo) {
        int message = articleService.insertArticle(articleVo);
        if (message == 0) {
            return new ReturnMessage("保存失败");
        }
        return new ReturnMessage("保存成功", articleVo);
    }


    @GetMapping("/list")
    public ReturnMessage queryArticle(ArticleVo articleVo){
        PageInfo<ArticleVo> articleList = articleService.queryArticle(articleVo);
        return new ReturnMessage("query success",articleList);
    }


    @PutMapping
    public ReturnMessage updateArticle(@RequestBody @Valid ArticleVo articalVo){
        int message = articleService.updateArticle(articalVo);
        if(message == 0){
            return new ReturnMessage("modify failed");
        }
        return new ReturnMessage("modify success",articalVo);
    }

    @GetMapping("/{id}")
    public ReturnMessage queryArticleById(@PathVariable Integer id){
        return new ReturnMessage("success", articleService.getArticleById(id));
    }

    @DeleteMapping("/{id}")
    public ReturnMessage deleteArticleById(@PathVariable Integer id){
        return new ReturnMessage("success", articleService.deleteArticleById(id));
    }






}
