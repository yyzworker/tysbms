package com.tys.security.service;


import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.MenuVo;
import com.tys.entity.vo.RoleVo;

import java.util.List;

/**
 * @author ：王志浩
 * @date ：Created in 2019-4-17 16:31
 */
public interface RoleService {

	/**
	 * 根据userId获取role集合
	 * 
	 * @param userId 用户id
	 * @return 返回角色信息
	 * */
	List<RoleVo> getRoleByUserid(int userId);

	List<MenuVo> getMenuByUserid(int userId);

    int insertRole(RoleVo role);

	PageInfo<RoleVo> queryRole(RoleVo role);

	int updateRole(RoleVo role);

	RoleVo getRoleAndMenuByUserid(Integer id);

	List<RoleVo> queryRoles();
}
