package com.tys.service.imp.dao;

import com.tys.entity.vo.Feedback;
import com.tys.service.imp.provider.FeedbackSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @Author 王志浩
 * @Date 2019/6/14 14:04
 **/
@Mapper
public interface FeedbackMapper {
    @Update({
            "update feedback set status = 0",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int processFeedback(Integer id);

    @SelectProvider(type = FeedbackSqlProvider.class, method = "queryFeedback")
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="member_id", property="memberId", jdbcType= JdbcType.INTEGER),
            @Result(column="contact", property="contact", jdbcType= JdbcType.VARCHAR),
            @Result(column="content", property="content", jdbcType= JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType= JdbcType.TIMESTAMP),
            @Result(column="status", property="status", jdbcType= JdbcType.INTEGER)
    })
    List<Feedback> queryFeedback(Feedback feedback);


}
