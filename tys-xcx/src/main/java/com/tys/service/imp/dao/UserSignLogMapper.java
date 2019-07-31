package com.tys.service.imp.dao;

import com.tys.entity.vo.Feedback;
import com.tys.entity.vo.UserSignLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface UserSignLogMapper {

    @Insert({
            "<script>",
            "insert into usersignlog ",
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "<if test=\"userId != null\">",
            "userid,",
            "</if>",
            "<if test=\"signDate != null\">",
            "signdate,",
            "</if>",
            "</trim>",
            "<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\"> ",
            "<if test=\"userId != null\">",
            "#{userId,jdbcType=VARCHAR},",
            "</if>",
            "<if test=\"signDate != null\">",
            "#{signDate,jdbcType=TIMESTAMP}",
            "</if>",
            "</trim>",
            "</script>"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    int insertUserSignLog(UserSignLog userSignLog);
}

