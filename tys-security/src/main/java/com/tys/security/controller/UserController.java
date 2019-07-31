package com.tys.security.controller;

import com.tys.entity.vo.UserVo;
import com.tys.security.core.SessionUser;
import com.tys.security.service.UserService;
import com.tys.util.vo.ReturnMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import com.github.pagehelper.PageInfo;

/**
 * @author 我是金角大王
 * @date 2018年1月25日 下午4:59:47
 */
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {
	
	@Autowired
	private UserService userservice;

	@PostMapping
	public ReturnMessage insertUser(@RequestBody @Valid UserVo user) {
		String password = new BCryptPasswordEncoder().encode(user.getUserPwd());
		user.setUserPwd(password);
		int message = userservice.insertUser(user);
		if (message == 0) {
			return new ReturnMessage("注册失败");
		}else if(message == 2){
			return new ReturnMessage("该登录账号已存在，请使用其他登录账号!");
		}
		return new ReturnMessage("注册成功", user);
	}
	
	@ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiImplicitParam(name = "userId", value = "用户主键", required = true, dataType = "int", paramType = "query")
	@GetMapping
	public ReturnMessage getUserInfo(Integer userId) {
		UserVo userVo = userservice.getUserByUserid(userId);
		return new ReturnMessage("成功", userVo);
	}
	
	@ApiOperation(value = "获取当前用户信息.", notes = "获取当前用户信息", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@GetMapping("/me")
	public ReturnMessage getMeUserInfo(HttpServletRequest request, HttpServletResponse response) {
		Object su = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(su.equals("anonymousUser")){
			return new ReturnMessage("没有登录");
		}else{
			SessionUser userDetails = (SessionUser) su;
			return new ReturnMessage("成功", userDetails);
		}
	}

	@GetMapping("/list")
	public ReturnMessage queryUser(UserVo user){
		PageInfo<UserVo> pageInfo = userservice.queryUser(user);
		return new ReturnMessage("success",pageInfo);
	}

	@PutMapping
	public ReturnMessage updateUser(@RequestBody @Valid UserVo user){
		int message = userservice.updateUser(user);
		if(message == 0){
			return new ReturnMessage("modify failed");
		}
		return new ReturnMessage("modify success",user);
	}

	@GetMapping("/{id}")
	public ReturnMessage queryUserById(@PathVariable Integer id){
		return new ReturnMessage("success",userservice.getUserAndRoleByUserid(id));
	}
}
