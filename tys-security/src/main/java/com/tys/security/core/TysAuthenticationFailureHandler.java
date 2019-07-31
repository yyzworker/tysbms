package com.tys.security.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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
public class TysAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(500);
		response.setContentType("application/vo");
	    response.setCharacterEncoding("utf-8");  
	    PrintWriter writer = response.getWriter();  
        writer.write(objectMapper.writeValueAsString(exception));  
        writer.flush();  
        writer.close();  
	}

}
