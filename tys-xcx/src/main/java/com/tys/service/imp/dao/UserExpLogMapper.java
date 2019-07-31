package com.tys.service.imp.dao;

import com.tys.entity.vo.UserExpLog;
import com.tys.entity.vo.UserShareLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface UserExpLogMapper {
    @Insert({
            "<script>",
            "insert into userexplog ",
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">",
            "<if test=\"userId != null\">",
            "userid,",
            "</if>",
            "<if test=\"eventDate != null\">",
            "eventdate,",
            "</if>",
            "<if test=\"point != null\">",
            "point,",
            "</if>",
            "<if test=\"eventName != null\">",
            "eventname,",
            "</if>",
            "<if test=\"eventType != null\">",
            "eventtype,",
            "</if>",
            "<if test=\"eventId != null\">",
            "eventid,",
            "</if>",
            "</trim>",
            "<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\"> ",
            "<if test=\"userId != null\">",
            "#{userId,jdbcType=INTEGER},",
            "</if>",
            "<if test=\"eventDate != null\">",
            "#{eventDate,jdbcType=TIMESTAMP},",
            "</if>",
            "<if test=\"point != null\">",
            "#{point,jdbcType=INTEGER},",
            "</if>",
            "<if test=\"eventName != null\">",
            "#{eventName,jdbcType=VARCHAR},",
            "</if>",
            "<if test=\"eventType != null\">",
            "#{eventType,jdbcType=VARCHAR},",
            "</if>",
            "<if test=\"eventId != null\">",
            "#{eventId,jdbcType=INTEGER},",
            "</if>",
            "</trim>",
            "</script>"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    int insertExpLog(UserExpLog userExpLog);
}
