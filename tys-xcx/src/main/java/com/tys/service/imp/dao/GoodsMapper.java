package com.tys.service.imp.dao;

import com.tys.entity.vo.Goods;
import com.tys.entity.vo.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface GoodsMapper {

    //获取商品列表
    @Select({
            "select",
            "id, name,point,wintime,createdate,demo,num",
            "from goods",
            "where status ='1'"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "point", property = "point", jdbcType = JdbcType.INTEGER),
            @Result(column = "wintime", property = "winTime", jdbcType = JdbcType.INTEGER),
            @Result(column = "createdate", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "demo", property = "demo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "num", property = "num", jdbcType = JdbcType.INTEGER)
    })
    List<Goods> getGoodsList();


    //获取最新的一条记录
    @Select({
            "select",
            "id, name,point,wintime,createdate,demo,num",
            "from goods",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "point", property = "point", jdbcType = JdbcType.INTEGER),
            @Result(column = "wintime", property = "winTime", jdbcType = JdbcType.INTEGER),
            @Result(column = "createdate", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "demo", property = "demo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "num", property = "num", jdbcType = JdbcType.INTEGER)
    })
    Goods getGoodsById(Integer id);
}
