package com.tys.security.controller;

import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.MenuVo;
import com.tys.security.service.MenuService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 王志浩
 * @date 2019年4月19日 下午4:59:47
 */
@RestController
@RequestMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MenuController {
	
	@Autowired
	private MenuService menuService;

	@GetMapping("/list")
	public ReturnMessage queryRole(){
		List<MenuVo> menulist = menuService.queryMenus();
		return new ReturnMessage("success",menulist);
	}

}
