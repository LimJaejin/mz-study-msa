package com.lguplus.fleta.config;

import java.lang.annotation.Annotation;
import java.util.Optional;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;

public class ParameterExpansionContextWrapper {

    private ParameterExpansionContextWrapper() {
    }

    public static <A extends Annotation> Optional<A> findAnnotation(ParameterExpansionContext context, Class<A> annotationType) {
        @SuppressWarnings("squid:S4738") // Swagger 3.x.x 버전에서는 Java 8 Optional 사용
        com.google.common.base.Optional<A> annotation = context.findAnnotation(annotationType);
        if (annotation.isPresent()) {
            return Optional.of(annotation.get());
        }
        return Optional.empty();
    }
}
