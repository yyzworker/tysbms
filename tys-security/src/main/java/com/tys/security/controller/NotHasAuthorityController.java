package com.tys.security.controller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 我是金角大王
 * @date 2018年1月25日 下午4:59:47
 */
public class NotHasAuthorityController extends LoginUrlAuthenticationEntryPoint {

	public NotHasAuthorityController(String loginFormUrl) {
		super(loginFormUrl);
	}

	// 当访问的资源没有权限，会调用这里
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
		// 返回json形式的错误信息
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE ,PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Content-Type");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/vo");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().println("{\"status\":1,\"message\":\"" + authException.getLocalizedMessage() + "\"}");
		response.getWriter().flush();
		response.getWriter().close();
	}
}
