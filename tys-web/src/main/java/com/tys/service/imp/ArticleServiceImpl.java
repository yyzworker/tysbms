package com.tys.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.ArticleVo;
import com.tys.entity.vo.Feedback;
import com.tys.service.ArticleService;
import com.tys.service.imp.dao.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public int insertArticle(ArticleVo articleVo) {
        articleVo.setStatus((byte)1);
        return articleMapper.insertSelective(articleVo);
    }

    @Override
    public PageInfo<ArticleVo> queryArticle(ArticleVo record) {
        PageInfo<ArticleVo> pageInfo = null;
        if(record.getCurrentPage() != null && record.getPageSize() != null){
            pageInfo = PageHelper.startPage(record.getCurrentPage(), record.getPageSize(),"update_time DESC").doSelectPageInfo(() -> articleMapper.queryArticle(record));
        }else {
            pageInfo = new PageInfo(articleMapper.queryArticle(record));
        }
        return pageInfo;

    }


    @Override
    public int updateArticle(ArticleVo articleVo) {
        return articleMapper.updateByPrimaryKeySelective(articleVo);
    }

    @Override
    public ArticleVo getArticleById(Integer id) {
        return articleMapper.selectArticleById(id);
    }

    @Override
    public int deleteArticleById(Integer id) {
        return articleMapper.deleteArticleById(id);
    }
}
