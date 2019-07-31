package com.tys.security.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.MenuVo;
import com.tys.entity.vo.UserVo;
import com.tys.security.service.MenuService;
import com.tys.security.service.UserService;
import com.tys.security.service.impl.dao.MenuMapper;
import com.tys.security.service.impl.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-2-27 16:31
 */
@Service
public class MenuServiceImpl implements MenuService {


	@Autowired
	private MenuMapper menumapper;

	@Override
	public List<MenuVo> queryMenus() {
		return menumapper.selectMenus();
	}
}
