package com.tys.service.imp.dao;

import com.tys.entity.vo.DetectScoreVo;
import com.tys.entity.vo.DrProblemSetting;
import com.tys.service.imp.provider.DrProblemSettingSqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface DrProblemSettingMapper {

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