package com.lguplus.fleta.exception;

import com.lguplus.fleta.data.dto.response.InnerResponseDto;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ExceptionHandler 등에서 처리하지 않는 엑셉션에 대해 처리
 */
@Slf4j
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController implements ErrorController {

    /**
     * 에러 API 반환
     */
    @RequestMapping
    public InnerResponseDto<Object> error(HttpServletRequest request) {
        HttpStatus httpStatus = getHttpStatus(request);
        String requestUri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        String errorMessage = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        log.error(">>> requestUri: {}, statusCode: {}, errorMessage: {}", requestUri, httpStatus, errorMessage);
        return InnerResponseDto.of(httpStatus);
    }

    private HttpStatus getHttpStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
