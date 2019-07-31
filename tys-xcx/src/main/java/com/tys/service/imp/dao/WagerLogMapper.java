package com.tys.service.imp.dao;

import com.tys.entity.vo.WagerLog;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
public interface WagerLogMapper {
    @Insert({
            "<script>",
            "insert into wagerlog ",
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "<if test=\"name != null\">",
            "name,",
            "</if>",
            "<if test=\"beginDate != null\">",
            "begindate,",
            "</if>",
            "<if test=\"endDate != null\">",
            "enddate,",
            "</if>",
            "<if test=\"lotteryTime != null\">",
            "lotterytime,",
            "</if>",
            "<if test=\"result != null\">",
            "result,",
            "</if>",
            "<if test=\"wagerValue != null\">",
            "wagervalue,",
            "</if>",
            "</trim>",
            "<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\"> ",
            "<if test=\"name != null\">",
            "#{name,jdbcType=VARCHAR},",
            "</if>",
            "<if test=\"beginDate != null\">",
            "#{beginDate,jdbcType=TIMESTAMP}",
            "</if>",
            "<if test=\"endDate != null\">",
            "#{endDate,jdbcType=TIMESTAMP}",
            "</if>",
            "<if test=\"lotteryTime != null\">",
            "#{lotteryTime,jdbcType=TIMESTAMP}",
            "</if>",
            "<if test=\"result != null\">",
            "#{result,jdbcType=VARCHAR}",
            "</if>",
            "<if test=\"wagerValue != null\">",
            "#{wagerValue,jdbcType=FLOAT}",
            "</if>",
            "</trim>",
            "</script>"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    int insertWagerLog(WagerLog wagerLog);

    //获取最新的一条记录
    @Select({
            "select",
            "id, name,begindate,enddate,lotterytime,result,wagervalue",
            "from wagerlog",
            "where wagervalue is null and status ='0'"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "begindate", property = "beginDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "enddate", property = "endDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "lotterytime", property = "lotteryTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "result", property = "result", jdbcType = JdbcType.VARCHAR),
            @Result(column = "wagervalue", property = "wagerValue", jdbcType = JdbcType.FLOAT),
    })
    WagerLog getNewRecord();
}
