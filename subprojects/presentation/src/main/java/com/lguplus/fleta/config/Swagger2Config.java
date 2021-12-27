package com.lguplus.fleta.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lguplus.fleta.data.dto.response.InnerResponseDto;
import com.lguplus.fleta.data.type.response.InnerResponseCodeType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableSwagger2
public class Swagger2Config {

    private static final String API_NAME = "MSA 보일러플레이트";
    private static final String API_VERSION = "1.0.0";
    private static final String API_DESCRIPTION = "MSA 보일러플레이트 API 명세서";

    private final ObjectMapper objectMapper;

    /**
     * localhost:8080/swagger-ui.html
     */
    @SneakyThrows
    @Bean
    public Docket api() {
        final List<ResponseMessage> responseMessages = Arrays.asList(
            new ResponseMessageBuilder().code(400)
                .message(this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(InnerResponseDto.of(InnerResponseCodeType.BAD_REQUEST))).build(),
            new ResponseMessageBuilder().code(404)
                .message(this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(InnerResponseDto.of(InnerResponseCodeType.NOT_FOUND))).build(),
            new ResponseMessageBuilder().code(500)
                .message(this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(InnerResponseDto.of(InnerResponseCodeType.INTERNAL_SERVER_ERROR))).build()
        );

        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .directModelSubstitute(Object.class, Void.class)
            .apiInfo(apiInfo())
            .globalResponseMessage(RequestMethod.GET, responseMessages)
            .globalResponseMessage(RequestMethod.POST, responseMessages)
            .globalResponseMessage(RequestMethod.PUT, responseMessages)
            .globalResponseMessage(RequestMethod.DELETE, responseMessages)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.lguplus.fleta.api"))
            .paths(PathSelectors.any())
            .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(API_NAME)
            .version(API_VERSION)
            .description(API_DESCRIPTION)
            .build();
    }
}
