package com.tys.service.imp.dao;

import com.tys.entity.vo.DrComplexSetting;
import com.tys.service.imp.provider.DrComplexSettingSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface DrComplexSettingMapper {
    @Update({
        "update dr_complexsetting set status = 0",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);


    @InsertProvider(type= DrComplexSettingSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(DrComplexSetting record);

    @Select({
        "select",
        "id, skin_type, description_type, description_content",
        "from dr_complexsetting",
        "where id = #{id,jdbcType=INTEGER} and status = 1"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="skin_type", property="skinType", jdbcType=JdbcType.TINYINT),
        @Result(column="description_type", property="descriptionType", jdbcType=JdbcType.TINYINT),
        @Result(column="description_content", property="descriptionContent", jdbcType=JdbcType.VARCHAR)
    })
    DrComplexSetting selectByPrimaryKey(Integer id);

    @UpdateProvider(type=DrComplexSettingSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DrComplexSetting record);

    @SelectProvider(type = DrComplexSettingSqlProvider.class, method = "query")
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="skin_type", property="skinType", jdbcType=JdbcType.TINYINT),
            @Result(column="description_type", property="descriptionType", jdbcType=JdbcType.TINYINT),
            @Result(column="description_content", property="descriptionContent", jdbcType=JdbcType.VARCHAR)
    })
    List<DrComplexSetting> query(DrComplexSetting record);

    @Select({
            "select",
            "id, skin_type,description_type, description_content",
            "from dr_complexsetting",
            "where status = 1  and skin_type = #{skinType}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="skin_type", property="skinType", jdbcType=JdbcType.TINYINT),
            @Result(column="description_type", property="descriptionType", jdbcType=JdbcType.TINYINT),
            @Result(column="description_content", property="descriptionContent", jdbcType=JdbcType.VARCHAR)
    })
    List<DrComplexSetting> selectDrComplexBySkinType(Byte skinType);
}