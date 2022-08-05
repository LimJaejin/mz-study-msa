package com.lguplus.fleta.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class Swagger2Config {

    @Value("${spring.application.name}")
    private String API_NAME;
    private static final String API_VERSION = "V1.0.0";
    private static final String API_DESCRIPTION = "MSA 보일러플레이트 API 명세서";
    /**
     * localhost:8080/swagger-ui/index.html
     */

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
            .info(new Info().title(API_NAME)
                .description(API_DESCRIPTION)
                .version(API_VERSION));
    }
}
