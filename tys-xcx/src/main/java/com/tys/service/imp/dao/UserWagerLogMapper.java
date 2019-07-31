package com.tys.service.imp.dao;

import com.tys.entity.vo.TagInfo;
import com.tys.entity.vo.UserWagerLog;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserWagerLogMapper {
    @Insert({
            "<script>",
            "insert into userwagerlog ",
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "<if test=\"userId != null\">",
            "userid,",
            "</if>",
            "<if test=\"wagerId != null\">",
            "wagerid,",
            "</if>",
            "<if test=\"createDate != null\">",
            "createdate,",
            "</if>",
            "<if test=\"usePoint != null\">",
            "usepoint,",
            "</if>",
            "<if test=\"type != null\">",
            "type,",
            "</if>",
            "</trim>",
            "<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\"> ",
            "<if test=\"userId != null\">",
            "#{userId,jdbcType=INTEGER},",
            "</if>",
            "<if test=\"wagerId != null\">",
            "#{wagerId,jdbcType=INTEGER},",
            "</if>",
            "<if test=\"createDate != null\">",
            "#{createDate,jdbcType=TIMESTAMP},",
            "</if>",
            "<if test=\"usePoint != null\">",
            "#{usePoint,jdbcType=INTEGER},",
            "</if>",
            "<if test=\"type != null\">",
            "#{type,jdbcType=VARCHAR},",
            "</if>",
            "</trim>",
            "</script>"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    int insertUserWagerLog(UserWagerLog userWagerLog);

    @Select({
            "<script>",
            "select",
            "id,userid,wagerid,createdate,usepoint,type,result,takepoint",
            "from userwagerlog where ",
            "userid = #{userId,jdbcType=INTEGER} limit 0 10 order by createdate desc",
            "</script>"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER),
            @Result(column="userid", property="userId", jdbcType= JdbcType.INTEGER),
            @Result(column="wagerid", property="wagerId", jdbcType= JdbcType.INTEGER),
            @Result(column="createdate", property="createDate", jdbcType= JdbcType.TIMESTAMP),
            @Result(column="usepoint", property="usePoint", jdbcType= JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType= JdbcType.VARCHAR),
            @Result(column="result", property="result", jdbcType= JdbcType.VARCHAR),
            @Result(column="wagervalue", property="wagerValue", jdbcType= JdbcType.INTEGER)
    })
    List<UserWagerLog> getUserWagerLogList(@Param("userId") Integer userId);

    @Select({
            "<script>",
            "select",
            "id,userid,wagerid,createdate,usepoint,type,result,takepoint",
            "from userwagerlog where ",
            "userid = #{userId,jdbcType=INTEGER}  and wagerid = #{wagerId,jdbcType=INTEGER}",
            "</script>"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER),
            @Result(column="userid", property="userId", jdbcType= JdbcType.INTEGER),
            @Result(column="wagerid", property="wagerId", jdbcType= JdbcType.INTEGER),
            @Result(column="createdate", property="createDate", jdbcType= JdbcType.TIMESTAMP),
            @Result(column="usepoint", property="usePoint", jdbcType= JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType= JdbcType.VARCHAR),
            @Result(column="result", property="result", jdbcType= JdbcType.VARCHAR),
            @Result(column="wagervalue", property="wagerValue", jdbcType= JdbcType.INTEGER)
    })
    UserWagerLog getUserWagerLogByUserId(@Param("userId") Integer userId,@Param("wagerId") Integer wagerId);

    @Select({
            "<script>",
            "select",
            "type, count(1) zs" ,
            "from userwagerlog where ",
            "wagerid = #{wagerId,jdbcType=INTEGER} group by type",
            "</script>"
    })
    @Results({
            @Result(column="type", property="type", jdbcType= JdbcType.INTEGER),
            @Result(column="zs", property="zs", jdbcType= JdbcType.INTEGER)
    })
   List<Map<String,Object>> getUserWagerByWagerId(@Param("wagerId") Integer wagerId);
}
