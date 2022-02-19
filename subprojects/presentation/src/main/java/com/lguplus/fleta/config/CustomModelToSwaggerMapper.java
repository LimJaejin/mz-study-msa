package com.lguplus.fleta.config;

import io.swagger.models.parameters.Parameter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2MapperImpl;

@Slf4j
@Primary
@Component("ServiceModelToSwagger2Mapper")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomModelToSwaggerMapper extends ServiceModelToSwagger2MapperImpl {

    @Override
    protected List<Parameter> parameterListToParameterList(List<springfox.documentation.service.Parameter> list) {
        if (list == null) {
            return Collections.emptyList();
        }

        return super.parameterListToParameterList(
            list.stream()
                .sorted(Comparator.comparingInt(springfox.documentation.service.Parameter::getOrder))
                .collect(Collectors.toList())
        );
    }

}
