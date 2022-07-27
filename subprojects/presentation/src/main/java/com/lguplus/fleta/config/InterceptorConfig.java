package com.lguplus.fleta.config;

import com.lguplus.fleta.interceptor.LogInterceptor;
import com.lguplus.fleta.interceptor.MetricInterceptor;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private static final List<String> EXCLUDE_PATHS = Arrays.asList("/webjars/**",
        "/swagger-resources/**",
        "/swagger*",
        "/csrf",
        "/error"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()).order(1).excludePathPatterns(EXCLUDE_PATHS);
        registry.addInterceptor(new MetricInterceptor()).order(2).excludePathPatterns(EXCLUDE_PATHS);
    }

}
