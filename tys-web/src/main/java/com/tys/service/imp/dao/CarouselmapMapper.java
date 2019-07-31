package com.tys.service.imp.dao;

import com.tys.entity.vo.CarouselmapVo;
import com.tys.entity.vo.UserVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


/**
* @author 王志浩
* @date 2019年4月26日 上午9:52:42
*/
@Mapper
public interface CarouselmapMapper {

	
	/**
     * 根据主键获取 Carouselmap
     *
     * @param id 主键
     * @return  返回轮播图集合
     * */
    @Results(id = "selectCarouselmapById", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "imgUrl", column = "img_url"),
			@Result(property = "useType", column = "use_type"),
            @Result(property = "jumpType", column = "jump_type"),
            @Result(property = "jumpUrl", column = "jump_url"),
            @Result(property = "effective", column = "effective"),
            @Result(property = "status", column = "status")})
    @Select("select id, name, img_url, use_type, jump_type, jump_url, effective,status "
            + " from carouselmap where id = #{id} and status = 1")
    CarouselmapVo selectCarouselmapById(int id);
	
	/**
	 * 创建轮播图
	 * 
	 * @param carouselmap 轮播图类
	 * @return 返回判断码：0：失败，1：成功
	 * */
	@Insert("insert into carouselmap (name,img_url,use_type,jump_type,jump_url,effective,status)"
			+ " values(#{name},#{imgUrl},#{useType},#{jumpType},#{jumpUrl},#{effective},1)")
	@SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "id", keyColumn = "id", resultType = String.class, before = false)
	int insertCarouselmap(CarouselmapVo carouselmap);

	/**
	 * 修改轮播图信息
	 * @param carouselmap
	 * @return
	 */
	@Update({
			"<script>",
			"update carouselmap ",
			" set name = #{name,jdbcType=VARCHAR},",
			" img_url = #{imgUrl,jdbcType=VARCHAR},",
			" use_type = #{useType,jdbcType=TINYINT},",
			" jump_type = #{jumpType,jdbcType=TINYINT},",
			" jump_url = #{jumpUrl,jdbcType=VARCHAR},",
			" img_url = #{imgUrl,jdbcType=VARCHAR},",
			" effective = #{effective,jdbcType=TINYINT},",
			" status = #{status,jdbcType=TINYINT}",
			" where id = #{id,jdbcType=INTEGER}",
			"</script>"
	})
	int updateByPrimaryKey(CarouselmapVo carouselmap);

	@Select({
			"<script>",
			"select id, name, img_url, use_type,jump_type, jump_url, effective,status,create_time ,create_user_id,update_time,update_user_id from carouselmap",
			"where status = 1 ",
			"<if test = \"name != null \">",
			"and name like CONCAT('%',#{name,jdbcType=VARCHAR},'%')",
			"</if>",
			"<if test = \"effective != null \">",
			"and effective = #{effective,jdbcType=TINYINT}",
			"</if>",
			"<if test = \"useType != null \">",
			"and use_type = #{useType,jdbcType=TINYINT}",
			"</if>",
			"</script>"
	})
	@Results({
			@Result(property = "id", column = "id",jdbcType=JdbcType.INTEGER, id = true),
			@Result(property = "name", column = "name",jdbcType=JdbcType.VARCHAR),
			@Result(property = "imgUrl", column = "img_url",jdbcType=JdbcType.VARCHAR),
			@Result(property = "useType", column = "use_type",jdbcType=JdbcType.TINYINT),
			@Result(property = "jumpType", column = "jump_type",jdbcType=JdbcType.TINYINT),
			@Result(property = "jumpUrl", column = "jump_url",jdbcType=JdbcType.VARCHAR),
			@Result(property = "effective", column = "effective",jdbcType=JdbcType.TINYINT),
			@Result(property = "status", column = "status",jdbcType=JdbcType.TINYINT),
			@Result(property = "createTime", column = "create_time",jdbcType=JdbcType.TIMESTAMP),
			@Result(property = "createUserId", column = "create_user_id",jdbcType=JdbcType.INTEGER),
			@Result(property = "updateTime", column = "update_time",jdbcType=JdbcType.TIMESTAMP),
			@Result(property = "updateUserId", column = "update_user_id",jdbcType=JdbcType.INTEGER)
	})
	List<CarouselmapVo> queryCarouselmaps(CarouselmapVo carouselmapVo);


    @Update({
            "<script>",
            "update carouselmap ",
            " set status = 0",
            " where id = #{id,jdbcType=INTEGER}",
            "</script>"
    })
    int deleteCarouselmapById(Integer id);



	@Results({
			@Result(column = "id",property = "id", jdbcType= JdbcType.INTEGER, id = true),
			@Result(column = "name",property = "name", jdbcType=JdbcType.VARCHAR)})
    @Select({
            "<script>",
            "select id, name ",
            " from carouselmap where status = 1 and name= #{name,jdbcType=VARCHAR} ",
            "<if test=\"id != null\">",
            " and id &lt;&gt; #{id,jdbcType=INTEGER}",
            "</if>",
            "</script>"
    })
	List<CarouselmapVo> exitsName(Integer id,String name);

	/**
	 * 查询所有可用的Carouselmap的imageUrl和jumpType
	 *
	 * @return 返回轮播图集合
	 * */
	@Results(id = "getAdvertiseInfo", value = {
			@Result(property = "imgUrl", column = "img_url"),
			@Result(property = "jumpType", column = "jump_type"),
			@Result(property = "updateTime", column = "update_time")
		})
	@Select({
			"<script> ",
			" select img_url, jump_type ,update_time",
			" from carouselmap",
			" where ",
			"<foreach collection=\"ids\"  index=\"index\" item=\"id\" separator=\"or\">",
			" id = #{id,jdbcType=INTEGER}",
			"</foreach> ",
			"</script>"
	})
	List<CarouselmapVo> getAdvertiseInfo(@Param("ids") List<String> ids);
}