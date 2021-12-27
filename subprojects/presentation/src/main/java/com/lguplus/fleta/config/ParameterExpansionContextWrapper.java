package com.lguplus.fleta.config;

import springfox.documentation.spi.service.contexts.ParameterExpansionContext;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class ParameterExpansionContextWrapper {

    private ParameterExpansionContextWrapper() { }

    public static <A extends Annotation> Optional<A> findAnnotation(ParameterExpansionContext context, Class<A> annotationType) {
        // TODO : SonarLint에 걸린 아래 Optional은 어떻게 처리??  by mzjjlim  on 2021.12.27
        com.google.common.base.Optional<A> annotation = context.findAnnotation(annotationType);
        if (annotation.isPresent()) {
            return Optional.of(annotation.get());
        }
        return Optional.empty();
    }
}
