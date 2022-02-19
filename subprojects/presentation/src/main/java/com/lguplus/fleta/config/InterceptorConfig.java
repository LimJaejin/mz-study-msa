package com.lguplus.fleta.config;

import com.lguplus.fleta.interceptor.LogInterceptor;
import com.lguplus.fleta.interceptor.MetricInterceptor;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final LogInterceptor logInterceptor;
    private final MetricInterceptor metricInterceptor;

    private static final List<String> EXCLUDE_PATHS = Arrays.asList("/webjars/**",
        "/swagger-resources/**",
        "/swagger*",
        "/csrf",
        "/error"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).order(1).excludePathPatterns(EXCLUDE_PATHS);
        registry.addInterceptor(metricInterceptor).order(2).excludePathPatterns(EXCLUDE_PATHS);
    }

}
