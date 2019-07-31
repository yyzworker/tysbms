package com.tys.service.imp.dao;

import com.tys.entity.vo.ArticleVo;
import com.tys.entity.vo.CarouselmapVo;
import com.tys.entity.vo.Feedback;
import com.tys.service.imp.provider.ArticleProvider;
import com.tys.service.imp.provider.FeedbackSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


/**
* @author 王志浩
* @date 2019年4月26日 上午9:52:42
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
			@Result(column="img_url", property="imgUrl", jdbcType= JdbcType.VARCHAR),
            @Result(property = "status", column = "status")})
    @Select("select id, article_title, article_content, article_type,img_url,article_url, status "
            + " from article where id = #{id} and status = 1")
	ArticleVo selectArticleById(int id);

	@SelectProvider(type = ArticleProvider.class, method = "queryArticle")
	@Results({
			@Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
			@Result(column="article_title", property="articleTitle", jdbcType= JdbcType.INTEGER),
			@Result(column="article_content", property="articleContent", jdbcType= JdbcType.VARCHAR),
			@Result(column="article_url", property="articleUrl", jdbcType= JdbcType.VARCHAR),
			@Result(column="img_url", property="imgUrl", jdbcType= JdbcType.VARCHAR),
			@Result(column="article_type", property="articleType", jdbcType= JdbcType.VARCHAR)
	})
	List<ArticleVo> queryArticle(ArticleVo record);

	/**
	 * 创建文章
	 * */
	@InsertProvider(type=ArticleProvider.class, method="insertSelective")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
	int insertSelective(ArticleVo record);

	/**
	 * 修改文章
	 */
	@UpdateProvider(type=ArticleProvider.class, method="updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ArticleVo record);

    @Update({
            "<script>",
            "update article set status = 0",
            " where id = #{id,jdbcType=INTEGER}",
            "</script>"
    })
    int deleteArticleById(Integer id);

}