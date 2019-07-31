package com.tys.security.controller;

import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.RoleVo;
import com.tys.entity.vo.UserVo;
import com.tys.security.core.SessionUser;
import com.tys.security.service.RoleService;
import com.tys.security.service.UserService;
import com.tys.util.vo.ReturnMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 王志浩
 * @date 2019年4月19日 下午4:59:47
 */
@RestController
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	@PostMapping
	public ReturnMessage insertRole(@RequestBody @Valid RoleVo role) {
		int message = roleService.insertRole(role);
		if (message == 0) {
			return new ReturnMessage("添加失败");
		}else if(message == 2){
			return new ReturnMessage("该名称已被使用，请修改!");
		}
		return new ReturnMessage("添加成功", role);
	}

	@GetMapping("/list")
	public ReturnMessage queryRole(RoleVo role){
		PageInfo<RoleVo> pageInfo = roleService.queryRole(role);
		return new ReturnMessage("success",pageInfo);
	}

	@PutMapping
	public ReturnMessage updateRole(@RequestBody @Valid RoleVo role){
		int message = roleService.updateRole(role);
		if(message == 0){
			return new ReturnMessage("modify failed");
		}
		return new ReturnMessage("modify success",role);
	}

	@GetMapping("/{id}")
	public ReturnMessage queryRoleById(@PathVariable Integer id){
		return new ReturnMessage("success",roleService.getRoleAndMenuByUserid(id));
	}

	@GetMapping("/listRoles")
	public ReturnMessage queryRole(){
		List<RoleVo> rolesInfo = roleService.queryRoles();
		return new ReturnMessage("success",rolesInfo);
	}
}
