package com.lguplus.fleta.exception;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;

@Slf4j
public class ParameterValidateException extends RuntimeException {

    public ParameterValidateException(final List<ObjectError> errors, String errorMsg) {
        super(errorMsg);
        log.warn(errors.stream()
            .map((ObjectError e) -> String.format("Error in object '%s': %s", e.getObjectName(), e.getDefaultMessage()))
            .collect(Collectors.joining(" && ")));
    }
}
