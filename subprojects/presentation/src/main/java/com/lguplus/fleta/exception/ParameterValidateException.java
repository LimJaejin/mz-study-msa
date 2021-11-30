package com.lguplus.fleta.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ParameterValidateException extends RuntimeException {
    public ParameterValidateException(final List<ObjectError> errors, String errorMsg) {
        super(errorMsg);

//        super(errors.stream()
//                .map((ObjectError e) -> String.format("Error in object '%s': %s", e.getObjectName(), e.getDefaultMessage()))
//                .collect(Collectors.joining(" && ")));
//      gf_Logging_New(gst_ss_info.pro_id, ":%s:%s] svc[%-18s] msg[정상적인 INPUT PARAM이 아닙니다.] [%s:%d]\n", rd1.c_sa_id, rd1.c_stb_mac, API_NAME, __FILE__, __LINE__);
        log.warn(errors.stream()
                .map((ObjectError e) -> String.format("Error in object '%s': %s", e.getObjectName(), e.getDefaultMessage()))
                .collect(Collectors.joining(" && ")));
    }
}
