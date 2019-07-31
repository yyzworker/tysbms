package com.tys.service.imp.dao;

import com.tys.entity.vo.CarouselmapVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CarouselMapMapper {

    @Select("select id, img_url, jump_type, jump_url from carouselmap where status = 1 and effective = 1 and use_type = 0")
    @Results({
        @Result(property = "id", column = "id", id = true),
        @Result(property = "imgUrl", column = "img_url"),
        @Result(property = "jumpType", column = "jump_type"),
        @Result(property = "jumpUrl", column = "jump_url"),
    })
    List<CarouselmapVo> selectEffective();
}
