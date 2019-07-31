package com.tys.service.imp.dao;

import com.tys.entity.vo.DetectRecord;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface DetectRecordMapper {


    @Select({"select",
            "id, member_id, equipment_id, detect_time, detect_location,detect_province,detect_city,data, result,upload_type",
            "from detectrecord",
            "where id = #{id,jdbcType=INTEGER} and status = 1"})
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="member_id", property="memberId", jdbcType=JdbcType.INTEGER),
            @Result(column="equipment_id", property="equipmentId", jdbcType=JdbcType.INTEGER),
            @Result(column="detect_time", property="detectTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="detect_location", property="detectLocation", jdbcType=JdbcType.VARCHAR),
            @Result(column="detect_province", property="detectProvince", jdbcType=JdbcType.VARCHAR),
            @Result(column="detect_city", property="detectCity", jdbcType=JdbcType.VARCHAR),
            @Result(column="data", property="data", jdbcType=JdbcType.VARCHAR),
            @Result(column="result", property="result", jdbcType=JdbcType.VARCHAR),
            @Result(column="upload_type", property="uploadType", jdbcType=JdbcType.TINYINT),
    })
    DetectRecord selectByPrimaryKey(Integer id);

    @Select({
            "select",
            "id, detect_time, result",
            "from detectrecord",
            "where member_id = #{memberId,jdbcType=INTEGER} and upload_type = 1 and result is not null and status = 1 order by detect_time desc"})
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="detect_time", property="detectTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="result", property="result", jdbcType=JdbcType.VARCHAR)
    })
    List<DetectRecord> selectByMemberId(Integer memberId);

    @Select({
            "select",
            "id, detect_time, result",
            "from detectrecord",
            "where member_id = #{memberId,jdbcType=INTEGER} and upload_type = 1 and result is not null and status = 1 order by detect_time desc limit 2"})
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="detect_time", property="detectTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="result", property="result", jdbcType=JdbcType.VARCHAR)
    })
    List<DetectRecord> selectTwoByMemberId(Integer memberId);

    @Select({
           "select",
            "id, member_id, equipment_id, detect_time,  detect_location, detect_province, detect_city, result, upload_type",
            "from detectrecord",
            "where member_id = #{mid,jdbcType=INTEGER} and result is not null and upload_type = 1 and status = 1",
            "order by detect_time desc limit 1" })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="member_id", property="memberId", jdbcType=JdbcType.INTEGER),
            @Result(column="equipment_id", property="equipmentId", jdbcType=JdbcType.INTEGER),
            @Result(column="detect_time", property="detectTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="detect_location", property="detectLocation", jdbcType=JdbcType.VARCHAR),
            @Result(column="detect_province", property="detectProvince", jdbcType=JdbcType.VARCHAR),
            @Result(column="detect_city", property="detectCity", jdbcType=JdbcType.VARCHAR),
            @Result(column="result", property="result", jdbcType=JdbcType.VARCHAR),
            @Result(column="upload_type", property="uploadType", jdbcType=JdbcType.TINYINT),
    })
    DetectRecord selectValidLastTime(Integer mid);

    @Select({
            "select three_path from detectrecord where member_id = #{mid,jdbcType=INTEGER} and three_path is not null and upload_type = 1 and status = 1 order by detect_time desc limit 1"
    })
    @ResultType(String.class)
    String selectThreePathByMid(Integer mid);

    @Select({"select count(1) from detectrecord where member_id = #{mid,jdbcType=INTEGER} and UNIX_TIMESTAMP(detect_time) > UNIX_TIMESTAMP(#{time,jdbcType=VARCHAR}) and upload_type = 1 and status = 1 "})
    @ResultType(Integer.class)
    Integer selectTimes(Integer mid , String time);

}
