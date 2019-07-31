package com.tys.service.imp.dao;

import com.tys.entity.vo.Feedback;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

/**
 * @Author haoxu
 * @Date 2019/6/14 14:04
 **/
@Mapper
public interface FeedbackMapper {

    @Insert({
            "<script>",
            "insert into feedback ",
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "<if test=\"memberId != null\">",
            "member_id,",
            "</if>",
            "<if test=\"contact != null\">",
            "contact,",
            "</if>",
            "<if test=\"content != null\">",
            "content,",
            "</if>",
            "<if test=\"status != null\">",
            "status,",
            "</if>",
            "</trim>",
            "<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\"> ",
            "<if test=\"memberId != null\">",
            "#{memberId,jdbcType=INTEGER},",
            "</if>",
            "<if test=\"contact != null\">",
            "#{contact,jdbcType=VARCHAR},",
            "</if>",
            "<if test=\"content != null\">",
            "#{content,jdbcType=VARCHAR},",
            "</if>",
            "<if test=\"status != null\">",
            "#{status,jdbcType=TINYINT},",
            "</if>",
            "</trim>",
            "</script>"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    int insertSelective(Feedback feedback);
}
