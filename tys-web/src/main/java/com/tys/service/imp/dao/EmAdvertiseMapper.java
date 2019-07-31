package com.tys.service.imp.dao;


import com.tys.entity.vo.EmAdvertise;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface EmAdvertiseMapper {

    @Insert({
            "<script>",
            "insert into em_advertise ",
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "<if test=\"emId != null\">",
            "em_id,",
            "</if>",
            "<if test=\"advertise != null\">",
            "advertise,",
            "</if>",
            "<if test=\"advertiseType != null\">",
            "advertise_type,",
            "</if>",
            "<if test=\"status != null\">",
            "status,",
            "</if>",
            "</trim>",
            "<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\"> ",
            "<if test=\"emId != null\">",
            "#{emId,jdbcType=INTEGER},",
            "</if>",
            "<if test=\"advertise != null\">",
            "#{advertise,jdbcType=VARCHAR},",
            "</if>",
            "<if test=\"advertiseType != null\">",
            "#{advertiseType,jdbcType=TINYINT},",
            "</if>",
            "<if test=\"status != null\">",
            "#{status,jdbcType=TINYINT},",
            "</if>",
            "</trim>",
            "</script>"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    int insertEmAdvertise(EmAdvertise record);

    @Update({
            "<script>",
            "update em_advertise",
            "<set>",
            "<if test=\"advertise != null\">",
            "advertise = #{advertise,jdbcType=VARCHAR},",
            "</if>",
            "<if test=\"advertiseType != null\">",
            "advertise_type = #{advertiseType,jdbcType=TINYINT},",
            "</if>",
            "<if test=\"status != null\">",
            "status = #{status,jdbcType=TINYINT},",
            "</if>",
            "</set>",
            "where ",
            "<foreach collection=\"emIds\"  index=\"index\" item=\"emid\" separator=\"or\">",
            " em_id = #{emid,jdbcType=INTEGER}",
            "</foreach> ",
            "</script>"
    })
    int updateMultipleEmAdvertise(EmAdvertise record);

    @Select({
            "select id,em_id,advertise,advertise_type,update_time from em_advertise where em_id = #{emId,jdbcType=INTEGER} and status = 1 "
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "em_id", property = "emId", jdbcType = JdbcType.INTEGER),
            @Result(column = "advertise", property = "advertise", jdbcType = JdbcType.VARCHAR),
            @Result(column = "advertise_type", property = "advertiseType", jdbcType = JdbcType.TINYINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    List<EmAdvertise> selectByEmId(Integer emId);

}
