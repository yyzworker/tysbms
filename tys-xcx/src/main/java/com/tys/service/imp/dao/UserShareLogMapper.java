package com.tys.service.imp.dao;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import com.tys.entity.vo.UserShareLog;
@Mapper
public interface UserShareLogMapper {
    @Insert({
            "<script>",
            "insert into usersharelog ",
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "<if test=\"userId != null\">",
            "userid,",
            "</if>",
            "<if test=\"shareDate != null\">",
            "sharedate,",
            "</if>",
            "</trim>",
            "<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\"> ",
            "<if test=\"userId != null\">",
            "#{userId,jdbcType=VARCHAR},",
            "</if>",
            "<if test=\"shareDate != null\">",
            "#{shareDate,jdbcType=TIMESTAMP}",
            "</if>",
            "</trim>",
            "</script>"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    int insertUserSignLog(UserShareLog UserShareLog);

    @Select({"select count(1) from usersharelog where userid = #{userid,jdbcType=INTEGER} and rwid = #{rwId, jdbcType=INTEGER}  "})
    @ResultType(Integer.class)
    int getUserShareCount(Integer userid,Integer rwId);
}
