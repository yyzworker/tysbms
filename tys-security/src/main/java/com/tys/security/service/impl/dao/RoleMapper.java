package com.tys.security.service.impl.dao;

import com.tys.entity.vo.MenuVo;
import com.tys.entity.vo.RoleVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


/**
* @author 王志浩
* @date 2019年4月17日 上午9:52:42
*/
@Mapper
public interface RoleMapper {

	
	/**
	 * 根据userId获取role
	 * 
	 * @param userId 用户主键
	 * @return 返回角色集合
	 * */
	@Results(id = "selectRoleByUserid", value = {
			@Result(property = "roleId", column = "role_id", id = true),
			@Result(property = "roleName", column = "role_name"),})
	@Select("select a.role_id,a.role_name "
			+ " from role a ,role_user b where a.role_id = b.role_id and b.user_id = #{userId}")
	 List<RoleVo> selectRoleByUserid(int userId);


	@Results({
			@Result(column="role_id", property="roleId", jdbcType= JdbcType.INTEGER, id=true),
			@Result(column="role_name", property="roleName", jdbcType=JdbcType.VARCHAR),
			@Result(column="role_desc", property="roleDesc", jdbcType=JdbcType.VARCHAR)
	})
	@Select({
			"<script>",
			"select role_id,role_name,role_desc ",
			" from role where 1=1 ",
			"<if test=\"roleName != null\">",
			" and role_name like concat('%',#{roleName,jdbcType=VARCHAR},'%')",
			"</if>",
			"</script>"
	})
	List<RoleVo> queryByEntity(RoleVo record);

	/**
	 * 创建角色
	 *
	 * */
	@Insert("insert into role (role_name,role_desc)"
			+ " values(#{roleName,jdbcType=VARCHAR},#{roleDesc,jdbcType=VARCHAR})")
	@SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "roleId", keyColumn = "role_id", resultType = Integer.class, before = false)
	int insertRole(RoleVo role);



	@Update({
			"update role",
			"set role_name = #{roleName,jdbcType=VARCHAR}",
			"where role_id = #{roleId,jdbcType=INTEGER}"
	})
	int updateRole(RoleVo role);

	@Results(id = "selectRoleByRoleId", value = {
			@Result(property = "roleId", column = "role_id", id = true),
			@Result(property = "roleName", column = "role_name"),
			@Result(property = "menus", column = "menus")})
	@Select("select a.role_id,a.role_name,GROUP_CONCAT(b.id) as menus  "
			+ " from role a,menu b,role_menu c where a.role_id = c.role_id and b.id = c.menu_id and a.role_id = #{roleId}")

	List<RoleVo> selectRoleByRoleId(int roleId);

	@Insert("insert into role_menu (role_id,menu_id)"
			+ " values(#{roleId},#{menus})")
	@SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "roleId", keyColumn = "role_id", resultType = Integer.class, before = false)
	int insertRoleAndMenu(RoleVo curRole);

	@Delete("delete from role_menu where role_id= #{roleId}")
	int deleteRoleMenu(Integer roleId);

	@Results(id = "selectMenuByUserid", value = {
			@Result(property = "id", column = "id", id = true),
			@Result(property = "menuName", column = "menu_name"),
			@Result(property = "path", column = "path")})
	@Select("SELECT " +
			"DISTINCT(b.id), " +
			"b.menu_name, " +
			"b.path " +
			"FROM " +
			"role a, " +
			"menu b, " +
			"role_user c, " +
			"role_menu d " +
			"WHERE " +
			"a.role_id = c.role_id " +
			"AND a.role_id = d.role_id " +
			"AND b.id = d.menu_id " +
			"AND c.user_id = #{userId} " +
            "ORDER BY sort")
    List<MenuVo> selectMenuByUserid(int userId);


	@Results(id = "queryRoles", value = {
			@Result(property = "roleId", column = "role_id", id = true),
			@Result(property = "roleName", column = "role_name")})
	@Select("select role_id,role_name  "
			+ " from role")
	List<RoleVo> queryRoles();
}