package com.kylin.faas.javatmp.config;

import com.kylin.faas.javatmp.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author LBW
 */
@Component
public class MyWebMvcConfig implements WebMvcConfigurer {
    private final RequestInterceptor requestInterceptor;

    public MyWebMvcConfig(RequestInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor);
    }
}
