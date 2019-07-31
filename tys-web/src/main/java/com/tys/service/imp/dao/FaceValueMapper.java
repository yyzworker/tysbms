package com.tys.service.imp.dao;

import com.tys.entity.vo.DetectScoreVo;
import com.tys.service.imp.provider.FaceValueSqlProvider;
import com.tys.entity.vo.FaceValue;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/3/6 11:30
 **/
@Mapper
public interface FaceValueMapper {

    @Update({
        "update facevalue set status = 0",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @InsertProvider(type= FaceValueSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(FaceValue record);


    @Select({
        "select",
        "id, sex, test_items, test_results, face_value",
        "from facevalue",
        "where id = #{id,jdbcType=INTEGER} and status = 1"
    })
    @Results({
        @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="sex", property="sex", jdbcType= JdbcType.TINYINT),
        @Result(column="test_items", property="testItems", jdbcType= JdbcType.TINYINT),
        @Result(column="test_results", property="testResults", jdbcType= JdbcType.TINYINT),
        @Result(column="face_value", property="faceValue", jdbcType= JdbcType.VARCHAR)
    })
    FaceValue selectByPrimaryKey(Integer id);


    @UpdateProvider(type= FaceValueSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(FaceValue record);

    @Update({
        "update facevalue",
        "set sex = #{sex,jdbcType=TINYINT},",
          "test_items = #{testItems,jdbcType=TINYINT},",
          "test_results = #{testResults,jdbcType=TINYINT},",
          "face_value = #{faceValue,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(FaceValue record);

    @SelectProvider(type = FaceValueSqlProvider.class, method = "querySelective")
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="sex", property="sex", jdbcType= JdbcType.TINYINT),
            @Result(column="test_items", property="testItems", jdbcType= JdbcType.TINYINT),
            @Result(column="test_results", property="testResults", jdbcType= JdbcType.TINYINT),
            @Result(column="face_value", property="faceValue", jdbcType= JdbcType.VARCHAR)
    })
    List<FaceValue> querySelective(FaceValue faceValue);

    @SelectProvider(type = FaceValueSqlProvider.class,method = "selectFaceValueBySorce")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "sex", property = "sex", jdbcType = JdbcType.TINYINT),
            @Result(column = "test_items", property = "testItems", jdbcType = JdbcType.TINYINT),
            @Result(column = "test_results", property = "testResults", jdbcType = JdbcType.TINYINT),
            @Result(column = "face_value", property = "faceValue", jdbcType = JdbcType.VARCHAR)
    })
    List<FaceValue> selectFaceValueBySorce(Byte sex, DetectScoreVo scoreVo);
}