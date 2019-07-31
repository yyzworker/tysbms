package com.tys.security.util;

import com.tys.security.core.SessionUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Author haoxu
 * @Date 2019/5/29 15:29
 **/
public class AuthenticationUtil {

    public static String getUserId(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if(securityContext != null){
            Authentication authentication = securityContext.getAuthentication();
            if(authentication != null){
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    return ((SessionUser) principal).getUserId();
                }
            }
        }
        return null;
    }
}
