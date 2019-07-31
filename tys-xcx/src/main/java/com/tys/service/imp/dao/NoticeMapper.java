package com.tys.service.imp.dao;

import com.tys.entity.vo.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface NoticeMapper {

    //获取最新的一条记录
    @Select({
            "select",
            "id, title,content,createdate",
            "from notice",
            "where status ='1'"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "createdate", property = "createDate", jdbcType = JdbcType.TIMESTAMP)
    })
    List<Notice> getNoticeList();
}
