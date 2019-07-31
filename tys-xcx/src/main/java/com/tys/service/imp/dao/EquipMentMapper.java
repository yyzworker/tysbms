package com.tys.service.imp.dao;

import com.tys.entity.vo.EquipMent;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/3/6 11:30
 **/
@Mapper
public interface EquipMentMapper {


    @Select({
            "<script> ",
            "select",
            "id, em_name, em_address, em_longitude, em_latitude, em_status, em_info, em_lasttime, em_imie, place_address, ",
            "place_info,em_photo",
            "from equipment",
            "where ",
            "<foreach collection=\"ids\"  index=\"index\" item=\"id\" separator=\"or\">",
            " id = #{id,jdbcType=INTEGER}",
            "</foreach> ",
            " order by field(id,",
            "<foreach collection=\"ids\"  index=\"index\" item=\"id\" separator=\",\">",
            "  #{id,jdbcType=INTEGER}",
            "</foreach> ",
            ")",
            "</script>"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="em_name", property="emName", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_address", property="emAddress", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_longitude", property="emLongitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_latitude", property="emLatitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_province", property="emProvince", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_city", property="emCity", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_status", property="emStatus", jdbcType=JdbcType.TINYINT),
            @Result(column="em_info", property="emInfo", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_lasttime", property="emLasttime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="em_imie", property="emImie", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_upush_id", property="emUpushId", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_photo", property="emPhoto", jdbcType=JdbcType.VARCHAR),
            @Result(column="place_address", property="placeAddress", jdbcType=JdbcType.VARCHAR),
            @Result(column="place_info", property="placeInfo", jdbcType=JdbcType.VARCHAR)
    })
    List<EquipMent> selectByPrimaryKeys(@Param("ids") List<Integer> ids);

}