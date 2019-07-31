package com.tys.service.imp.dao;

import com.tys.entity.vo.DetectMemberVo;
import com.tys.entity.vo.DetectRecord;
import com.tys.service.imp.provider.DetectRecordSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface DetectRecordMapper {
    @Update({
        "update detectrecord set status = 0",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @InsertProvider(type= DetectRecordSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(DetectRecord record);

    @Select({
        "select",
        "id, member_id, equipment_id, detect_time, detect_location,data, result, photo_path,three_path",
        "from detectrecord",
        "where id = #{id,jdbcType=INTEGER} and status = 1"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="member_id", property="memberId", jdbcType=JdbcType.INTEGER),
        @Result(column="equipment_id", property="equipmentId", jdbcType=JdbcType.INTEGER),
        @Result(column="detect_time", property="detectTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="detect_location", property="detectLocation", jdbcType=JdbcType.VARCHAR),
        @Result(column="data", property="data", jdbcType=JdbcType.VARCHAR),
        @Result(column="result", property="result", jdbcType=JdbcType.VARCHAR),
        @Result(column="smoothness", property="smoothness", jdbcType=JdbcType.TINYINT),
        @Result(column="photo_path", property="photoPath", jdbcType=JdbcType.VARCHAR),
        @Result(column="three_path", property="threePath", jdbcType=JdbcType.VARCHAR)
    })
    DetectRecord selectByPrimaryKey(Integer id);

    @Select({
            "select",
            "id, member_id, equipment_id, detect_time, detect_location, result, photo_path,three_path",
            "from detectrecord",
            "where member_id = #{mid,jdbcType=INTEGER} and detect_time < #{detectTime,jdbcType=TIMESTAMP} and upload_type = 1 and status = 1 and result is not null ",
            "order by detect_time desc limit 1"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="member_id", property="memberId", jdbcType=JdbcType.INTEGER),
            @Result(column="equipment_id", property="equipmentId", jdbcType=JdbcType.INTEGER),
            @Result(column="detect_time", property="detectTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="detect_location", property="detectLocation", jdbcType=JdbcType.VARCHAR),
            @Result(column="result", property="result", jdbcType=JdbcType.VARCHAR),
            @Result(column="smoothness", property="smoothness", jdbcType=JdbcType.TINYINT),
            @Result(column="photo_path", property="photoPath", jdbcType=JdbcType.VARCHAR),
            @Result(column="three_path", property="threePath", jdbcType=JdbcType.VARCHAR)
    })
    DetectRecord selectPreRecord(Integer mid, Timestamp detectTime);

    @UpdateProvider(type=DetectRecordSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DetectRecord record);

    @Update({
            "update detectrecord set three_path = #{threePath,jdbcType=VARCHAR} where id = #{id,jdbcType=INTEGER}"
    })
    int updateThreePathByPrimaryKey(Integer id,String threePath);

    @SelectProvider(type = DetectRecordSqlProvider.class, method = "queryDetectMember")
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="member_id", property="memberId", jdbcType=JdbcType.INTEGER),
            @Result(column="equipment_id", property="equipmentId", jdbcType=JdbcType.INTEGER),
            @Result(column="detect_time", property="detectTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="detect_location", property="detectLocation", jdbcType=JdbcType.VARCHAR),
            @Result(column="detect_province", property="detectProvince", jdbcType=JdbcType.VARCHAR),
            @Result(column="detect_city", property="detectCity", jdbcType=JdbcType.VARCHAR),
            @Result(column="photo_path", property="photoPath", jdbcType=JdbcType.VARCHAR),
            @Result(column="result", property="result", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType= JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType= JdbcType.VARCHAR),
            @Result(column="sex", property="sex", jdbcType= JdbcType.TINYINT),
            @Result(column="birthdate", property="birthdate", jdbcType= JdbcType.TIMESTAMP),
            @Result(column="age", property="age", jdbcType= JdbcType.INTEGER),
            @Result(column="height", property="height", jdbcType= JdbcType.INTEGER),
            @Result(column="weight", property="weight", jdbcType= JdbcType.INTEGER),
            @Result(column="job_tag", property="jobTag", jdbcType= JdbcType.VARCHAR),
            @Result(column="hobby_tag", property="hobbyTag", jdbcType= JdbcType.VARCHAR),
            @Result(column="registration_date", property="registrationDate", jdbcType= JdbcType.TIMESTAMP),
            @Result(column="detections_number", property="detectionsNumber", jdbcType= JdbcType.INTEGER),
            @Result(column="detections_lasttime", property="detectionsLasttime", jdbcType= JdbcType.TIMESTAMP),
            @Result(column="avatar_url", property="avatarUrl", jdbcType= JdbcType.VARCHAR),
            @Result(column="inviter", property="inviter", jdbcType= JdbcType.VARCHAR),
            @Result(column="em_name", property="emName", jdbcType= JdbcType.VARCHAR)
    })
    List<DetectMemberVo> queryDetectMember(DetectMemberVo detectMemberVo);

}