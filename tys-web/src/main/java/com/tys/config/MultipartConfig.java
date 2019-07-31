package com.tys.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @Author haoxu
 * @Date 2019/6/14 10:13
 **/
@Configuration
public class MultipartConfig {
    //在启动类里面添加MultipartConfigElement 配置类
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("/home/tmp");//指定临时文件路径，这个路径可以随便写
        return factory.createMultipartConfig();
    }
}
