package com.tys.service.imp.dao;

import com.tys.entity.vo.ArticleVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


/**
* @author haoxu
*/
@Mapper
public interface ArticleMapper {

	/**
     * 根据主键获取
     * */
    @Results(id = "selectArticleById", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "articleTitle", column = "article_title"),
            @Result(property = "articleContent", column = "article_content"),
			@Result(property = "articleType", column = "article_type"),
			@Result(column="article_url", property="articleUrl", jdbcType= JdbcType.VARCHAR),
			@Result(column="img_url", property="imgUrl", jdbcType= JdbcType.VARCHAR)
    })
    @Select("select id, article_title, article_content, article_type ,article_url,img_url"
            + " from article where id = #{id} and status = 1")
	ArticleVo selectArticleById(int id);

	@Select({"select id, article_title, article_type, article_url,img_url from article where status = 1"})
	@Results({
			@Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
			@Result(column="article_title", property="articleTitle", jdbcType= JdbcType.INTEGER),
			@Result(column="article_url", property="articleUrl", jdbcType= JdbcType.VARCHAR),
			@Result(column="img_url", property="imgUrl", jdbcType= JdbcType.VARCHAR),
			@Result(column="article_type", property="articleType", jdbcType= JdbcType.VARCHAR)
	})
	List<ArticleVo> queryArticle();

}