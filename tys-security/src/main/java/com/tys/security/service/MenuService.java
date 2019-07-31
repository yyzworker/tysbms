package com.tys.security.service;


import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.MenuVo;
import com.tys.entity.vo.RoleVo;

import java.util.List;

/**
 * @author ：王志浩
 * @date ：Created in 2019-4-17 16:31
 */
public interface MenuService {
	List<MenuVo> queryMenus();
}
