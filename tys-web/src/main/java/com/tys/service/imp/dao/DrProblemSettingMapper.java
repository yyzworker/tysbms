package com.tys.service.imp.dao;

import com.tys.entity.vo.DetectScoreVo;
import com.tys.entity.vo.DrProblemSetting;
import com.tys.service.imp.provider.DrProblemSettingSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface DrProblemSettingMapper {
    @Update({
        "update dr_problemsetting set status = 0",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @InsertProvider(type=DrProblemSettingSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(DrProblemSetting record);

    @Select({
        "select",
        "id, analysis_project, level, min_value, max_value, skin_type,sensitive_type, description_type, ",
        "description_content",
        "from dr_problemsetting",
        "where id = #{id,jdbcType=INTEGER} and status = 1"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="analysis_project", property="analysisProject", jdbcType=JdbcType.TINYINT),
        @Result(column="level", property="level", jdbcType=JdbcType.TINYINT),
        @Result(column="min_value", property="minValue", jdbcType=JdbcType.DOUBLE),
        @Result(column="max_value", property="maxValue", jdbcType=JdbcType.DOUBLE),
        @Result(column="skin_type", property="skinType", jdbcType=JdbcType.TINYINT),
        @Result(column="sensitive_type", property="sensitiveType", jdbcType=JdbcType.TINYINT),
        @Result(column="description_type", property="descriptionType", jdbcType=JdbcType.TINYINT),
        @Result(column="description_content", property="descriptionContent", jdbcType=JdbcType.VARCHAR),
    })
    DrProblemSetting selectByPrimaryKey(Integer id);

    @UpdateProvider(type= DrProblemSettingSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DrProblemSetting record);

    @SelectProvider(type = DrProblemSettingSqlProvider.class,method = "query")
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="analysis_project", property="analysisProject", jdbcType=JdbcType.TINYINT),
            @Result(column="level", property="level", jdbcType=JdbcType.TINYINT),
            @Result(column="min_value", property="minValue", jdbcType=JdbcType.DOUBLE),
            @Result(column="max_value", property="maxValue", jdbcType=JdbcType.DOUBLE),
            @Result(column="skin_type", property="skinType", jdbcType=JdbcType.TINYINT),
            @Result(column="sensitive_type", property="sensitiveType", jdbcType=JdbcType.TINYINT),
            @Result(column="description_type", property="descriptionType", jdbcType=JdbcType.TINYINT),
            @Result(column="description_content", property="descriptionContent", jdbcType=JdbcType.VARCHAR),
    })
    List<DrProblemSetting> query(DrProblemSetting record);

    @SelectProvider(type = DrProblemSettingSqlProvider.class,method = "selectDrProblemByScore")
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="analysis_project", property="analysisProject", jdbcType=JdbcType.TINYINT),
            @Result(column="level", property="level", jdbcType=JdbcType.TINYINT),
            @Result(column="min_value", property="minValue", jdbcType=JdbcType.DOUBLE),
            @Result(column="max_value", property="maxValue", jdbcType=JdbcType.DOUBLE),
            @Result(column="skin_type", property="skinType", jdbcType=JdbcType.TINYINT),
            @Result(column="sensitive_type", property="sensitiveType", jdbcType=JdbcType.TINYINT),
            @Result(column="description_type", property="descriptionType", jdbcType=JdbcType.TINYINT),
            @Result(column="description_content", property="descriptionContent", jdbcType=JdbcType.VARCHAR),
    })
    List<DrProblemSetting> selectDrProblemByScore(Byte skinType, DetectScoreVo scoreVo);
}