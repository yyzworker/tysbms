package com.tys.security.service.impl.dao;

import com.tys.entity.vo.MenuVo;
import com.tys.entity.vo.UserVo;
import com.tys.security.service.impl.provider.UserSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


/**
* @author 我是金角大王
* @date 2017年11月22日 上午9:52:42
*/
@Mapper
public interface MenuMapper {
	@Results(id = "selectMenus", value = {
			@Result(property = "id", column = "id", id = true),
			@Result(property = "pid", column = "pid"),
			@Result(property = "menuName", column = "menu_name"),
			@Result(property = "path", column = "path")
	})
	@Select({
			"<script>",
			"select id,pid,menu_name,path ",
			" from menu order by sort",
			"</script>"
	})
	List<MenuVo> selectMenus();
}