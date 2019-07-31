package com.tys.service.imp.dao;

import com.tys.entity.vo.GrowthTrendVo;
import com.tys.entity.vo.MemberAnalysisVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;



/**
* @author 王志浩
* @date 2019年5月7日 上午9:52:42
*/
@Mapper
public interface GrowthTrendMapper {

	@Results({
			@Result(column = "days",property = "time", jdbcType= JdbcType.VARCHAR),
			@Result(column = "count",property = "count", jdbcType=JdbcType.INTEGER)})
    @Select({
            "<script>",
            "select DATE_FORMAT(detect_time,'%Y%m%d') days,count(id) count  ",
            " from detectrecord where status = 1 group by days order by days asc ",
            "</script>"
    })
	List<GrowthTrendVo> listDetectRecordByDay();

	@Results({
			@Result(column = "months",property = "time", jdbcType= JdbcType.VARCHAR),
			@Result(column = "count",property = "count", jdbcType=JdbcType.INTEGER)})
	@Select({
			"<script>",
			"select DATE_FORMAT(detect_time,'%Y%m') months,count(id) count  ",
			" from detectrecord where status = 1 group by months order by months asc ",
			"</script>"
	})
	List<GrowthTrendVo> listDetectRecordByMonth();

	@Results({
			@Result(column = "weeks",property = "time", jdbcType= JdbcType.VARCHAR),
			@Result(column = "count",property = "count", jdbcType=JdbcType.INTEGER)})
	@Select({
			"<script>",
			"select DATE_FORMAT(detect_time,'%Y%u') weeks,count(id) count  ",
			" from detectrecord where status = 1 group by weeks order by weeks asc ",
			"</script>"
	})
	List<GrowthTrendVo> listDetectRecordByWeek();

    @Results({
            @Result(column = "days",property = "time", jdbcType= JdbcType.VARCHAR),
            @Result(column = "count",property = "count", jdbcType=JdbcType.INTEGER)})
    @Select({
            "<script>",
            "select DATE_FORMAT(registration_date,'%Y%m%d') days,count(id) count  ",
            " from memberinfo where status = 1 group by days order by days asc ",
            "</script>"
    })
    List<GrowthTrendVo> listMemberByDay();

    @Results({
            @Result(column = "months",property = "time", jdbcType= JdbcType.VARCHAR),
            @Result(column = "count",property = "count", jdbcType=JdbcType.INTEGER)})
    @Select({
            "<script>",
            "select DATE_FORMAT(registration_date,'%Y%m') months,count(id) count  ",
            " from memberinfo where status = 1 group by months order by months asc ",
            "</script>"
    })
    List<GrowthTrendVo> listMemberByMonth();

    @Results({
            @Result(column = "weeks",property = "time", jdbcType= JdbcType.VARCHAR),
            @Result(column = "count",property = "count", jdbcType=JdbcType.INTEGER)})
    @Select({
            "<script>",
            "select DATE_FORMAT(registration_date,'%Y%u') weeks,count(id) count  ",
            " from memberinfo where status = 1 group by weeks order by weeks asc ",
            "</script>"
    })
    List<GrowthTrendVo> listMemberByWeek();

    @Results({
            @Result(column = "xb",property = "time", jdbcType= JdbcType.VARCHAR),
            @Result(column = "count",property = "count", jdbcType=JdbcType.INTEGER)})
    @Select({
            "<script>",
            "select CASE sex WHEN '1' THEN '男' WHEN '2' THEN '女' ELSE '其他' END  as xb,count(1) as count ",
            " from memberinfo where status = 1 group by xb ",
            "</script>"
    })
    List<GrowthTrendVo> listMemberSex();


    @Results({
            @Result(column = "ageRatio",property = "ageRatio", jdbcType= JdbcType.INTEGER),
            @Result(column = "man",property = "man", jdbcType=JdbcType.INTEGER),
            @Result(column = "woman",property = "woman", jdbcType= JdbcType.INTEGER),
            @Result(column = "total",property = "total", jdbcType=JdbcType.INTEGER)})
    @Select({
            "<script>",
            "SELECT INTERVAL(age,0,16,21,26,31,36,41,46,51,56,61) AS ageRatio, SUM(CASE WHEN sex=1 THEN 1 ELSE 0 END) AS man,",
            "SUM(CASE WHEN sex=2 THEN 1 ELSE 0 END) AS woman,SUM(CASE WHEN 1=1 THEN 1 ELSE 0 END) AS total ",
            " FROM memberinfo where status = 1 GROUP BY ageRatio ",
            "</script>"
    })
    List<MemberAnalysisVo> listAgeRatio();
}