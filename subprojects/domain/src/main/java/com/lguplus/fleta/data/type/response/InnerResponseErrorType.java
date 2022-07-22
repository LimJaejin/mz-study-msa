package com.lguplus.fleta.data.type.response;

import java.util.EnumSet;
import java.util.Locale;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * HTTP API 표준 응답 Error 코드 (응답 코드, 메시지는 MessageSource에 정의 : messages/response*.yml)
 * @version 0.2.1
 */
public enum InnerResponseErrorType {
    PARAMETER_ERROR,
    INTERNAL_SERVER_ERROR;

    private String code;
    private String message;

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }


    /**
     * ResponseErrorType의 프로퍼티 설정 (MessageSource 이용)
     */
    @Slf4j
    @RequiredArgsConstructor
    @Component
    static class ResponseErrorTypePropertySetter {

        private static final String MESSAGE_CODE_PREFIX = "responseErrorType";
        private static final Locale DEFAULT_LOCALE = Locale.getDefault();

        private final MessageSource messageSource;

        @PostConstruct
        void postConstruct() {
            for (InnerResponseErrorType type : EnumSet.allOf(InnerResponseErrorType.class)) {
                String code = getMessage("code", type.name());
                String message = getMessage("message", type.name());
                if (log.isTraceEnabled()) {
                    log.trace(">>> MessageSource : {}.{} : {}, {}",
                        MESSAGE_CODE_PREFIX, type.name(), code, message);
                }
                setProperty(type, code, message);
            }
        }

        private void setProperty(InnerResponseErrorType type, String code, String message) {
            type.code = code;
            type.message = message;
        }

        private String getMessage(String propName, String name) {
            String msgCode = MESSAGE_CODE_PREFIX + "." + name + "." + propName;
            return messageSource.getMessage(msgCode, null, name, DEFAULT_LOCALE);
        }
    }
}
