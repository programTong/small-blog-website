package com.tongtongbigboy.blog.config;

import com.tongtongbigboy.blog.interceptor.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport {
    @Autowired
    private JwtFilter jwtFilter;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> exList = new ArrayList<>();
        exList.add("/**/login");
        exList.add("/**/register");
        registry.addInterceptor(jwtFilter).
                addPathPatterns("/**").
                excludePathPatterns(exList);

    }
}
