package com.tys.service.imp.dao;

import com.tys.entity.vo.GrowthTrendVo;
import com.tys.entity.vo.HomeVo;
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
public interface HomeMapper {

	@Results({
			@Result(column = "man",property = "memberMan", jdbcType= JdbcType.VARCHAR),
			@Result(column = "women",property = "memberWoman", jdbcType=JdbcType.VARCHAR),
            @Result(column = "total",property = "memberTotal", jdbcType= JdbcType.VARCHAR),
            @Result(column = "newAdd",property = "memberNewAdd", jdbcType=JdbcType.VARCHAR)})
	@Select({
			"<script>",
			" SELECT  SUM(CASE WHEN sex=1 THEN 1 ELSE 0 END) AS man,SUM(CASE WHEN sex=2 THEN 1 ELSE 0 END) AS women,SUM(CASE WHEN 1=1 THEN 1 ELSE 0 END)  ",
            " AS total,SUM(CASE WHEN TO_DAYS(now()) - TO_DAYS(registration_date) = 1 THEN 1 ELSE 0 END) AS newAdd ",
			" FROM memberinfo where status = 1 ",
			"</script>"
	})
	List<HomeVo> listMember();

	@Results({
			@Result(column = "man",property = "detectMan", jdbcType= JdbcType.VARCHAR),
			@Result(column = "women",property = "detectWoman", jdbcType=JdbcType.VARCHAR),
            @Result(column = "total",property = "detectTotal", jdbcType= JdbcType.VARCHAR),
            @Result(column = "newAdd",property = "detectNewAdd", jdbcType=JdbcType.VARCHAR)})
    @Select({
            "<script>",
            "select SUM(CASE WHEN b.sex=1 THEN 1 ELSE 0 END) AS man,SUM(CASE WHEN b.sex=2 THEN 1 ELSE 0 END) AS women,SUM(CASE WHEN 1=1 THEN 1 ELSE 0 END) AS ",
            " total,SUM(CASE WHEN TO_DAYS(now()) - TO_DAYS(detect_time) = 1 THEN 1 ELSE 0 END) AS newAdd ",
            " from detectrecord a left join memberinfo b on a.member_id = b.id where a.status = 1 ",
            "</script>"
    })
	List<HomeVo> listDetectRecord();

    @Results({
            @Result(column = "normal",property = "equipmentNormal", jdbcType= JdbcType.VARCHAR),
            @Result(column = "abnormal",property = "equipmentAbNormal", jdbcType=JdbcType.VARCHAR),
            @Result(column = "yichang",property = "equipmentShutDown", jdbcType= JdbcType.VARCHAR),
            @Result(column = "total",property = "equipmentTotal", jdbcType=JdbcType.VARCHAR)})
    @Select({
            "<script>",
            " SELECT  SUM(CASE WHEN em_status=1 THEN 1 ELSE 0 END) AS normal,SUM(CASE WHEN em_status=2 THEN 1 ELSE 0 END) AS abnormal,SUM(CASE WHEN em_status=3 ",
            " THEN 1 ELSE 0 END) AS yichang,SUM(CASE WHEN 1=1 THEN 1 ELSE 0 END) AS total FROM equipment where status = 1 ",
            "</script>"
    })
    List<HomeVo> listEquipment();
}