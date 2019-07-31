package com.tys.security.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.MenuVo;
import com.tys.entity.vo.RoleVo;
import com.tys.security.service.RoleService;
import com.tys.security.service.impl.dao.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author ：王志浩
 * @date ：Created in 2019-4-17 16:31
 */
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper rolemapper;

	@Override
	public List<RoleVo> getRoleByUserid(int userId) {
		return rolemapper.selectRoleByUserid(userId);
	}

	@Override
	public List<MenuVo> getMenuByUserid(int userId) {
		return rolemapper.selectMenuByUserid(userId);
	}

	@Override
	@Transactional
	public int insertRole(RoleVo role) {
		RoleVo rv = new RoleVo();
		rv.setRoleName(role.getRoleName());
		rv.setPageSize(1);
		rv.setCurrentPage(1);
		List<RoleVo> roleVoList_before = queryRole(rv).getList();
		if(!ObjectUtils.isEmpty(roleVoList_before)){
			return 2;
		}
		int result = rolemapper.insertRole(role);
		List<RoleVo> roleVoList = queryRole(rv).getList();
		if(!ObjectUtils.isEmpty(roleVoList) && result == 1){
			int roleId = roleVoList.get(0).getRoleId();
			//插入角色时，需要同时往role_menu表中插值
			String menuIds = role.getMenus();
			String[] menuIdArray = null;
			menuIdArray = menuIds.split(",");
			for(String menuId:menuIdArray){
				RoleVo curRole = new RoleVo();
				curRole.setRoleId(roleId);
				curRole.setMenus(menuId);
				rolemapper.insertRoleAndMenu(curRole);
			}
		}
		return result ;
	}

	@Override
	public PageInfo<RoleVo> queryRole(RoleVo role) {
		PageInfo<RoleVo> pageInfo = null;
		if(role.getCurrentPage() != null && role.getPageSize() != null){
			pageInfo = PageHelper.startPage(role.getCurrentPage(), role.getPageSize()).doSelectPageInfo(() -> rolemapper.queryByEntity(role));
		}else {
			pageInfo = new PageInfo(rolemapper.queryByEntity(role));
		}
		return pageInfo;
	}

	@Override
	public int updateRole(RoleVo role) {
		int result = rolemapper.updateRole(role);
		//更新角色时，先删除掉映射表中的数据，再重新插入数据
		rolemapper.deleteRoleMenu(role.getRoleId());
		//插入角色时，需要同时往role_menu表中插值
		String menuIds = role.getMenus();
		String[] menuIdArray = null;
		menuIdArray = menuIds.split(",");
		for(String menuId:menuIdArray){
			RoleVo curRole = new RoleVo();
			curRole.setRoleId(role.getRoleId());
			curRole.setMenus(menuId);
			rolemapper.insertRoleAndMenu(curRole);
		}
		return result;
	}

	@Override
	public RoleVo getRoleAndMenuByUserid(Integer id) {
		List<RoleVo> roles = rolemapper.selectRoleByRoleId(id);
		if(roles.size()==0){
			return null;
		}
		return roles.get(0);
	}

	@Override
	public List<RoleVo> queryRoles() {
		return rolemapper.queryRoles();
	}
}
