package com.java6.demoJV6.config;

import com.java6.demoJV6.component.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

	@Bean(name = "customJwtFilter")
    public FilterRegistrationBean<JwtFilter> jwtFilter(JwtFilter filter) {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/admin/*", "/user/*");
        return registrationBean;
    }
}
