package com.tys.security.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-2-28 16:31
 */
@Component
public class TysAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setStatus(200);
        response.setContentType("application/vo;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(authentication));
        writer.flush();
        writer.close();
    }

}
