package com.tys.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.ArticleVo;
import com.tys.service.ArticleService;
import com.tys.service.imp.dao.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author haoxu
 * @Date 2019/7/4 16:26
 **/
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public PageInfo<ArticleVo> queryArticle(Integer pageNum, Integer pageSize,String orderBy) {
        PageInfo<ArticleVo> pageInfo = null;
        if(pageNum != null && pageSize != null){
            if(orderBy == null){
                orderBy = "update_time";
            }
            orderBy += " DESC";
            pageInfo = PageHelper.startPage(pageNum, pageSize,orderBy).doSelectPageInfo(() -> articleMapper.queryArticle());
        }else {
            pageInfo = new PageInfo(articleMapper.queryArticle());
        }
        return pageInfo;
    }

    @Override
    public ArticleVo selectArticleVoById(Integer id) {
        return articleMapper.selectArticleById(id);
    }
}
