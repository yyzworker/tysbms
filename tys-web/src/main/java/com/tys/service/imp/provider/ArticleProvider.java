package com.tys.service.imp.provider;


import com.tys.entity.vo.ArticleVo;
import org.apache.ibatis.jdbc.SQL;

public class ArticleProvider {

    public String insertSelective(ArticleVo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("article");

        if (record.getArticleTitle() != null) {
            sql.VALUES("article_title", "#{articleTitle,jdbcType=VARCHAR}");
        }

        if (record.getArticleContent() != null) {
            sql.VALUES("article_content", "#{articleContent,jdbcType=VARCHAR}");
        }

        if (record.getArticleType() != null) {
            sql.VALUES("article_type", "#{articleType,jdbcType=TINYINT}");
        }

        if (record.getArticleUrl() != null) {
            sql.VALUES("article_url", "#{articleUrl,jdbcType=VARCHAR}");
        }

        if (record.getImgUrl() != null) {
            sql.VALUES("img_url", "#{imgUrl,jdbcType=VARCHAR}");
        }

        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=TINYINT}");
        }
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(ArticleVo record) {
        SQL sql = new SQL();
        sql.UPDATE("article");

        if (record.getArticleContent() != null) {
            sql.SET("article_content = #{articleContent,jdbcType=VARCHAR}");
        }

        if (record.getArticleTitle() != null) {
            sql.SET("article_title = #{articleTitle,jdbcType=VARCHAR}");
        }

        if (record.getArticleType() != null) {
            sql.SET("article_type = #{articleType,jdbcType=TINYINT}");
        }

        if (record.getArticleUrl() != null) {
            sql.SET("article_url = #{articleUrl,jdbcType=VARCHAR}");
        }

        if (record.getImgUrl() != null) {
            sql.SET("img_url = #{imgUrl,jdbcType=VARCHAR}");
        }

        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=TINYINT}");
        }

        sql.WHERE("id = #{id,jdbcType=INTEGER}");

        return sql.toString();
    }

    public String queryArticle(ArticleVo record) {
        return new SQL() {
            {
                SELECT("id, article_title, article_content, article_type, article_url, img_url");
                FROM("article");
                WHERE("status = 1");
                if (record.getArticleTitle()!= null) {
                    WHERE("article_title like concat('%',#{articleTitle,jdbcType=VARCHAR},'%')");
                }
                if (record.getArticleType() != null){
                    WHERE("article_type = #{articleType,jdbcType=TINYINT}");
                }
                ORDER_BY("create_time desc");
            }
        }.toString();
    }
}
