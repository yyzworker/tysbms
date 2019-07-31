package com.tys.service.imp.dao;

import com.tys.entity.vo.MyCosmeticsVo;
import com.tys.service.imp.provider.MemberInfoSqlProvider;
import com.tys.entity.vo.MemberInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/3/6 11:30
 **/
@Mapper
public interface MemberInfoMapper {


    @InsertProvider(type=MemberInfoSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(MemberInfo record);


    @Select({
        "select",
        "id, name, phone, sex, birthdate, age, height, weight, bust, waistline, buttline, job_tag, hobby_tag, registration_date, detections_number, silent_number, detections_lasttime, avatar_url,inviter ",
        "from memberinfo",
        "where id = #{id,jdbcType=INTEGER} and status = 1"
    })
    @Results({
        @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType= JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType= JdbcType.VARCHAR),
        @Result(column="sex", property="sex", jdbcType= JdbcType.TINYINT),
        @Result(column="birthdate", property="birthdate", jdbcType= JdbcType.TIMESTAMP),
        @Result(column="age", property="age", jdbcType= JdbcType.INTEGER),
        @Result(column="height", property="height", jdbcType= JdbcType.INTEGER),
        @Result(column="weight", property="weight", jdbcType= JdbcType.INTEGER),
        @Result(column = "bust", property = "bust", jdbcType = JdbcType.INTEGER),
        @Result(column = "waistline", property = "waistline", jdbcType = JdbcType.INTEGER),
        @Result(column = "buttline", property = "buttline", jdbcType = JdbcType.INTEGER),
        @Result(column="job_tag", property="jobTag", jdbcType= JdbcType.VARCHAR),
        @Result(column="hobby_tag", property="hobbyTag", jdbcType= JdbcType.VARCHAR),
        @Result(column="registration_date", property="registrationDate", jdbcType= JdbcType.TIMESTAMP),
        @Result(column="detections_number", property="detectionsNumber", jdbcType= JdbcType.INTEGER),
        @Result(column="silent_number", property="silentNumber", jdbcType= JdbcType.INTEGER),
        @Result(column="detections_lasttime", property="detectionsLasttime", jdbcType= JdbcType.TIMESTAMP),
        @Result(column="avatar_url", property="avatarUrl", jdbcType= JdbcType.VARCHAR),
        @Result(column="inviter", property="inviter", jdbcType= JdbcType.INTEGER)
    })
    MemberInfo selectByPrimaryKey(Integer id);


    @UpdateProvider(type=MemberInfoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MemberInfo record);


    @SelectProvider(type = MemberInfoSqlProvider.class, method = "queryMemberInfo")
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="name", property="name", jdbcType= JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType= JdbcType.VARCHAR),
            @Result(column="sex", property="sex", jdbcType= JdbcType.TINYINT),
            @Result(column="birthdate", property="birthdate", jdbcType= JdbcType.TIMESTAMP),
            @Result(column="age", property="age", jdbcType= JdbcType.INTEGER),
            @Result(column="height", property="height", jdbcType= JdbcType.INTEGER),
            @Result(column="weight", property="weight", jdbcType= JdbcType.INTEGER),
            @Result(column = "bust", property = "bust", jdbcType = JdbcType.INTEGER),
            @Result(column = "waistline", property = "waistline", jdbcType = JdbcType.INTEGER),
            @Result(column = "buttline", property = "buttline", jdbcType = JdbcType.INTEGER),
            @Result(column="job_tag", property="jobTag", jdbcType= JdbcType.VARCHAR),
            @Result(column="hobby_tag", property="hobbyTag", jdbcType= JdbcType.VARCHAR),
            @Result(column="registration_date", property="registrationDate", jdbcType= JdbcType.TIMESTAMP),
            @Result(column="detections_number", property="detectionsNumber", jdbcType= JdbcType.INTEGER),
            @Result(column="silent_number", property="silentNumber", jdbcType= JdbcType.INTEGER),
            @Result(column="detections_lasttime", property="detectionsLasttime", jdbcType= JdbcType.TIMESTAMP),
            @Result(column="avatar_url", property="avatarUrl", jdbcType= JdbcType.VARCHAR),
            @Result(column="inviter", property="inviter", jdbcType= JdbcType.INTEGER)
    })
    List<MemberInfo> queryMemberInfo(MemberInfo memberInfo);

    @Select({
            "select cosmetic_id from mycosmetics where member_id = #{mid,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="cosmetic_id", property="cosmeticId", jdbcType= JdbcType.INTEGER),
    })
    List<MyCosmeticsVo> queryMyCosmetics(Integer mid);
}