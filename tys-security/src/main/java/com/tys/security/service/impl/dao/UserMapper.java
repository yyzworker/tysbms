package com.tys.security.service.impl.dao;

import com.tys.entity.vo.UserVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


/**
* @author 我是金角大王
* @date 2017年11月22日 上午9:52:42
*/
@Mapper
public interface UserMapper {
	/**
	 * 根据username获取user
	 * 
	 * @param userName 用户名
	 * @return 返回用户集合
	 * */
	@Results(id = "selectUserByUsername", value = {
			@Result(property = "userId", column = "user_id", id = true),
			@Result(property = "userName", column = "user_name"),
			@Result(property = "userPwd", column = "user_pwd"),
			@Result(property = "userPhone", column = "user_phone"),
			@Result(property = "displayName", column = "display_name"),
			@Result(property = "userRank", column = "user_rank"),
			@Result(property = "userLock", column = "user_lock")})
	@Select("select user_id, user_name, user_pwd, user_phone, display_name,user_rank, user_lock "
			+ "from user where user_name = #{userName}")
	List<UserVo> selectUserByUsername(String userName);
	
	/**
	 * 根据userid获取user
	 * 
	 * @param userId 用户主键
	 * @return 返回用户集合
	 * */
	@Results(id = "selectUserByUserid", value = {
			@Result(property = "userId", column = "user_id", id = true),
			@Result(property = "userName", column = "user_name"),
			@Result(property = "userPwd", column = "user_pwd"),
			@Result(property = "userPhone", column = "user_phone"),
			@Result(property = "displayName", column = "display_name"),
			@Result(property = "userRank", column = "user_rank"),
			@Result(property = "userLock", column = "user_lock")})
	@Select("select user_id, user_name, user_phone, display_name, user_rank, user_lock "
			+ "from user where user_id = #{userId}")
	List<UserVo> selectUserByUserid(int userId);
	
	/**
	 * 创建用户
	 * 
	 * @param user 用户类
	 * @return 返回判断码：0：失败，1：成功
	 * */
	@Insert("insert into user (user_name,user_pwd,user_phone,display_name,user_rank,user_lock)"
			+ " values(#{userName},#{userPwd},#{userPhone},#{displayName},#{userRank},#{userLock})")
	@SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "userId", keyColumn = "user_id", resultType = String.class, before = false)
	int insertUser(UserVo user);

	@Results({
			@Result(column="user_id", property="userId", jdbcType= JdbcType.INTEGER, id=true),
			@Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
			@Result(column="user_phone", property="userPhone", jdbcType=JdbcType.VARCHAR),
			@Result(column="display_name", property="displayName", jdbcType=JdbcType.VARCHAR),
			@Result(column="user_lock", property="userLock", jdbcType= JdbcType.TINYINT)
	})
	@Select({
			"<script>",
			"select user_id,user_name,user_pwd,display_name,user_phone,user_lock ",
			" from user ",
			"<if test=\"displayName != null\">",
			" and display_name like concat('%',#{displayName,jdbcType=VARCHAR},'%')",
			"</if>",
			"<if test=\"userPhone != null\">",
			" and user_phone like concat('%',#{userPhone,jdbcType=VARCHAR},'%')",
			"</if>",
			"<if test=\"userLock != null\">",
			" and user_lock = #{userLock,jdbcType=VARCHAR}",
			"</if>",
			" GROUP BY user_id ",
			"</script>"
	})
	List<UserVo> queryByEntity(UserVo record);


	@Update({
			"<script>",
			"update user ",
			" set user_phone = #{userPhone,jdbcType=VARCHAR},",
			" display_name = #{displayName,jdbcType=VARCHAR},",
			"<if test=\"userPwd != null \">",
			"  user_pwd = #{userPwd,jdbcType=VARCHAR},",
			"</if>",
			" user_lock = #{userLock,jdbcType=TINYINT}",
			" where user_id = #{userId,jdbcType=INTEGER}",
			"</script>"
	})
	int updateByPrimaryKey(UserVo record);


	/**
	 * 根据userid获取user;
	 *
	 * @param userId 用户主键
	 * @return 返回用户集合
	 * */
	@Results(id = "selectUserAndRoleByUserid", value = {
			@Result(property = "userId", column = "user_id", id = true),
			@Result(property = "userName", column = "user_name"),
			@Result(property = "userPwd", column = "user_pwd"),
			@Result(property = "userPhone", column = "user_phone"),
			@Result(property = "displayName", column = "display_name"),
			@Result(property = "userRank", column = "user_rank"),
			@Result(property = "userLock", column = "user_lock"),
			@Result(property = "roles", javaType=List.class, column="user_id",
					many=@Many(select="com.tys.security.service.impl.dao.RoleMapper.selectRoleByUserid"))
	})
	@Select({
			"<script>",
			"select user_id,user_name,user_pwd,display_name,user_phone,user_lock ",
			" from user where user_id = #{userId}",
			"</script>"
	})
	List<UserVo> selectUserAndRoleByUserid(int userId);

	/**
	 * 往role_user表中插值
	 * @return
	 */
	@Insert("insert into role_user (role_id,user_id)"
			+ " values(#{roles},#{userId})")
	@SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "roleUserId", keyColumn = "role_user_id", resultType = String.class, before = false)
	int insertRoleUser(int roles,String userId);

    @Delete("delete from role_user where user_id= #{userId}")
	int deleteRoleUser(String userId);

}