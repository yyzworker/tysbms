package com.tys.security.core;

import com.tys.security.controller.NotHasAuthorityController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


/**
 * @author ：我是金角大王
 * @date ：Created in 2019-2-27 16:31
 */
@Configuration
@EnableWebSecurity
public class TysSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler tysAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler tysAuthenticationFailureHandler;

    /**
     * 设置 HTTP验证规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/", "/login").permitAll()
                .antMatchers("/", "/login/form").permitAll()

                //模块权限 start
                //会员管理权限：
                .antMatchers( "/memberinfo/**").hasRole("member")
                //检测报告权限：
                .antMatchers( "/detectrecord/**").hasRole("detectrecord")
                //账号列表权限：
                .antMatchers( "/user/**").hasRole("user")
                //权限组设置权限：
                .antMatchers( "/role/**").hasRole("role")
                //轮播图设置权限：
                .antMatchers( "/carouselmap/**").hasRole("carouselmap")
                //文章设置权限：
                .antMatchers( "/article/**").hasRole("article")
                //意见反馈权限：
                .antMatchers( "/feedback/**").hasRole("feedback")
                //产品库权限：
                .antMatchers( "/commodity/**").hasRole("commodity")
                //成份库权限：
                .antMatchers( "/composition/**").hasRole("composition")
                //颜值印象设置权限：
                .antMatchers( "/facevalue/**").hasRole("facevalue")
                //综合设置权限：
                .antMatchers( "/drcomplex/**").hasRole("complexsetting")
                //方案设置权限：
                .antMatchers( "/drproblem/**").hasRole("problemsetting")
                //标签设置权限：
                .antMatchers( "/taginfo/**").hasRole("tagsetting")
                //设备管理权限：
                .antMatchers( "/equipment/**").hasRole("equipment")
                //广告设置权限：
                .antMatchers( "/carouselmap/**").hasRole("adsetting")
                //版本设置权限：
                .antMatchers( "/equipment/addApkVersion/**").hasRole("versionsetting")
                .antMatchers("/getOssToken/**").hasAnyRole("commodity","carouselmap","adsetting","equipment","article")
                .antMatchers("/equipment/android/**","/equipment/getAdvertiseInfo/**","/detectrecord/threed/**","/detectrecord/score/**").permitAll()
                 //模块权限 end

                .and()
                .logout()
                .logoutSuccessUrl("/index.html")
                .and().exceptionHandling()
                .authenticationEntryPoint(new NotHasAuthorityController("/login/authority"))
                .and()
                //开启formLogin默认配置
                .formLogin()
                //请求时未登录跳转接口
                .loginPage("/login/authority").permitAll()
                .successHandler(tysAuthenticationSuccessHandler)
                .failureHandler(tysAuthenticationFailureHandler)
                .loginProcessingUrl("/login")
                .and()
                .logout()//配置注销
                //注销接口
                .logoutUrl("/logout")
                //注销成功跳转接口
                .logoutSuccessUrl("/login/logout").permitAll()
                //删除自定义的cookie
                .deleteCookies("myCookie")
                //post登录接口，登录验证由系统实现
                .and()
                .csrf().disable();//禁用csrf
    }

    /**
     * 修改security配置 修改userDetails的实现
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置UserDetailsService
        auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
