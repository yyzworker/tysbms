package com.tys.service.imp.dao;

import com.tys.service.imp.provider.EquipMentSqlProvider;
import com.tys.entity.vo.EquipMent;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/3/6 11:30
 **/
@Mapper
public interface EquipMentMapper {

    @Update({
        "update equipment set status = 0",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Select({"select id from equipment where status = 1"})
    List<Integer> selectAllIds();


    @InsertProvider(type=EquipMentSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(EquipMent record);


    @Select({
        "select",
        " em.id id, em_name, em_address, em_longitude, em_latitude,em_province, em_city, em_status, em_hardware, em_info, em_lasttime, em_imie, em_upush_id, em_version, place_address, ",
        " place_info,em_photo,ad.advertise advertise,ad.advertise_type advertise_type,ad.status status ",
        "from equipment em left join em_advertise ad on em.id = ad.em_id",
        " where em.id = #{id,jdbcType=INTEGER} and em.status = 1"
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
            @Result(column="em_hardware", property="emHardware", jdbcType=JdbcType.TINYINT),
            @Result(column="em_info", property="emInfo", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_lasttime", property="emLasttime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="em_imie", property="emImie", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_iccid", property="emIccid", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_number", property="emNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_upush_id", property="emUpushId", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_version", property="emVersion", jdbcType=JdbcType.VARCHAR),
            @Result(column="place_address", property="placeAddress", jdbcType=JdbcType.VARCHAR),
            @Result(column="place_info", property="placeInfo", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_photo", property="emPhoto", jdbcType=JdbcType.VARCHAR),
            @Result(column="advertise", property="advertise", jdbcType=JdbcType.VARCHAR),
            @Result(column="advertise_type", property="advertiseType", jdbcType=JdbcType.TINYINT),
            @Result(column="status", property="status", jdbcType=JdbcType.TINYINT)
    })
    EquipMent selectByPrimaryKey(Integer id);

    @Select({
            "select",
            "em.id id, em_name, em_address, em_longitude, em_latitude,em_province, em_city, em_status, em_hardware, em_info, em_lasttime, em_imie,em_iccid,em_number, em_upush_id, em_version, place_address,  ",
            "place_info,ad.advertise advertise,ad.advertise_type advertise_type,ad.status status",
            "from equipment em left join em_advertise ad on em.id = ad.em_id",
            "where em_imie = #{emImie,jdbcType=INTEGER} and em.status = 1"
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
            @Result(column="em_hardware", property="emHardware", jdbcType=JdbcType.TINYINT),
            @Result(column="em_info", property="emInfo", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_lasttime", property="emLasttime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="em_imie", property="emImie", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_iccid", property="emIccid", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_number", property="emNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_upush_id", property="emUpushId", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_version", property="emVersion", jdbcType=JdbcType.VARCHAR),
            @Result(column="place_address", property="placeAddress", jdbcType=JdbcType.VARCHAR),
            @Result(column="place_info", property="placeInfo", jdbcType=JdbcType.VARCHAR),
            @Result(column="advertise", property="advertise", jdbcType=JdbcType.VARCHAR),
            @Result(column="advertise_type", property="advertiseType", jdbcType=JdbcType.TINYINT),
            @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
    })
    EquipMent selectByImie(String emImie);


    @UpdateProvider(type=EquipMentSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(EquipMent record);

    @Update({
            "update equipment",
            "set em_lasttime = #{lasttime,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateLasttime(Integer id, Timestamp lasttime);

    @Update({
            "update equipment",
            "set em_hardware = #{emHardware,jdbcType=TINYINT}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateHardware(Integer id, Byte emHardware);


    @SelectProvider(type = EquipMentSqlProvider.class,method = "queryByEntity")
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="em_name", property="emName", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_address", property="emAddress", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_longitude", property="emLongitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_latitude", property="emLatitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_province", property="emProvince", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_city", property="emCity", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_status", property="emStatus", jdbcType=JdbcType.TINYINT),
            @Result(column="em_hardware", property="emHardware", jdbcType=JdbcType.TINYINT),
            @Result(column="em_info", property="emInfo", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_lasttime", property="emLasttime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="em_imie", property="emImie", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_iccid", property="emIccid", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_number", property="emNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_upush_id", property="emUpushId", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_version", property="emVersion", jdbcType=JdbcType.VARCHAR),
            @Result(column="place_address", property="placeAddress", jdbcType=JdbcType.VARCHAR),
            @Result(column="place_info", property="placeInfo", jdbcType=JdbcType.VARCHAR),
            @Result(column="em_photo", property="emPhoto", jdbcType=JdbcType.VARCHAR),
            @Result(column="advertise", property="advertise", jdbcType=JdbcType.VARCHAR),
            @Result(column="advertise_type", property="advertiseType", jdbcType=JdbcType.TINYINT)
    })
    List<EquipMent> queryByEntity(EquipMent record);

    @Select("select em_imie from equipment where id = #{id,jdbcType=INTEGER} and status = 1")
    String queryImieById(Integer id);

    @Select("select em_upush_id from equipment where id = #{id,jdbcType=INTEGER} and status = 1")
    String queryUpushIdById(Integer id);

    @Update({
            "update equipment",
            "set em_status = #{emStatus,jdbcType=TINYINT}",
            "where em_imie = #{emImie,jdbcType=VARCHAR}"
    })
    int updateEMStatusByImie(String emImie,Byte emStatus);

    @Update({
            "update equipment",
            "set em_status = #{emStatus,jdbcType=TINYINT}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateEMStatusById(Integer id,Byte emStatus);

}