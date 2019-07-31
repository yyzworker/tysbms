package com.tys.service.imp.dao;

import com.tys.entity.vo.DetectScoreVo;
import com.tys.entity.vo.FaceValue;
import com.tys.service.imp.provider.FaceValueSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

/**
 * @Author haoxu
 * @Date 2019/3/6 11:30
 **/
@Mapper
public interface FaceValueMapper {


    @SelectProvider(type = FaceValueSqlProvider.class,method = "selectFaceValueBySorce")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "sex", property = "sex", jdbcType = JdbcType.TINYINT),
            @Result(column = "test_items", property = "testItems", jdbcType = JdbcType.TINYINT),
            @Result(column = "test_results", property = "testResults", jdbcType = JdbcType.TINYINT),
            @Result(column = "face_value", property = "faceValue", jdbcType = JdbcType.VARCHAR)
    })
    List<FaceValue> selectFaceValueBySorce(Byte sex,DetectScoreVo scoreVo);


}