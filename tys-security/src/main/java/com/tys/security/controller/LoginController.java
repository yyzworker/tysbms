package com.tys.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 我是金角大王
 * @date 2018年1月25日 下午4:59:47
 */
@RestController
public class LoginController {

    @RequestMapping("/login/authority")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String LoginAuthority(HttpServletRequest servletRequest, HttpServletResponse response) {
        return "没有权限";
    }

    @RequestMapping("/")
    public String LoginSuccess(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html");
        return "<script>window.location.href='http://www.5icoding.top'</script>";
    }

}
