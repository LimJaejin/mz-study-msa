package com.lguplus.fleta.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"com.lguplus.fleta.provider.feign"})
public class OpenFeignConfig {

}
