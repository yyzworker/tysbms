package com.tys.service.imp.dao;

import com.tys.entity.vo.DrComplexSetting;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface DrComplexSettingMapper {

    @Select({
            "<script>",
            "select",
            "id, skin_type ,description_type, description_content",
            "from dr_complexsetting",
            "where status = 1 and skin_type in  ",
            "<foreach item='skinType' index='index' collection='skinTypes' open='(' separator=',' close=')'>",
            "#{skinType,jdbcType=TINYINT}",
            "</foreach>",
            "</script>"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="skin_type", property="skinType", jdbcType=JdbcType.TINYINT),
        @Result(column="description_type", property="descriptionType", jdbcType=JdbcType.TINYINT),
        @Result(column="description_content", property="descriptionContent", jdbcType=JdbcType.VARCHAR)
    })
    List<DrComplexSetting> selectDrComplexBySkinType(@Param("skinTypes")List<Byte> skinTypes);

}