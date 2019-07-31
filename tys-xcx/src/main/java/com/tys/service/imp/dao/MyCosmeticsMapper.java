package com.tys.service.imp.dao;

import com.tys.entity.vo.MemberInfo;
import com.tys.entity.vo.MyCosmeticsVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface MyCosmeticsMapper {
    @Insert({"<script>",
            "insert into mycosmetics ",
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "<if test=\"memberId != null\">",
            "member_id,",
            "</if>",
            "<if test=\"cosmeticId != null\">",
            "cosmetic_id,",
            "</if>",
            "<if test=\"typeCode != null\">",
            "type_code,",
            "</if>",
            "</trim>",
            "<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\"> ",
            "<if test=\"memberId != null\">",
            "#{memberId,jdbcType=INTEGER},",
            "</if>",
            "<if test=\"cosmeticId != null\">",
            "#{cosmeticId,jdbcType=VARCHAR},",
            "</if>",
            "<if test=\"typeCode != null\">",
            "#{typeCode,jdbcType=INTEGER},",
            "</if>",
            "</trim>",
            "</script>"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "mycosmeticsId", before = false, resultType = Integer.class)
    int insertMyCosmetics(MyCosmeticsVo record);



    @Select({
            "select",
            "mycosmetics_id,cosmetic_id,type_code",
            "from mycosmetics",
            "where member_id = #{memberId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "mycosmetics_id", property = "mycosmeticsId", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "cosmetic_id", property = "cosmeticId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type_code", property = "typeCode", jdbcType = JdbcType.INTEGER)
    })
    List<MyCosmeticsVo> queryMyCosmetics(Integer memberId);


    @Update({
            "delete from mycosmetics",
            "where member_id = #{memberId,jdbcType=INTEGER}  and  cosmetic_id =#{cosmeticId,jdbcType=VARCHAR}"
    })
    int deleteMyCosmetics(MyCosmeticsVo record);



}
