package com.tys.service.imp.dao;

import com.tys.entity.vo.TagInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface TagInfoMapper {

    @Select({
            "select",
            "tag_content",
            "from taginfo",
            "where tag_type = #{type,jdbcType=TINYINT} and tag_status = 1 and status = 1 "
    })
    List<String> getTagInfos(Integer type);

    @Select({
            "<script>",
            "select",
            "tag_type,tag_content",
            "from taginfo where",
            "<foreach collection='types' index='index' item='tagType' separator='or'  open='(' close=') and'>",
            "tag_type = #{tagType} </foreach> ",
            "tag_status = 1 and status = 1 order by tag_type",
            "</script>"
    })
    @Results({
            @Result(column="tag_type", property="tagType", jdbcType= JdbcType.TINYINT),
            @Result(column="tag_content", property="tagContent", jdbcType= JdbcType.VARCHAR)
    })
    List<TagInfo> getTagsInfos(@Param("types") List<Integer> types);

}
