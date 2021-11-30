package com.lguplus.fleta.exception;

import com.lguplus.fleta.data.dto.response.InnerResponseDto;
import com.lguplus.fleta.data.type.response.InnerResponseCodeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * ExceptionHandler 등에서 처리하지 않는 엑셉션에 대해 처리
 */
@Slf4j
@RequiredArgsConstructor
@ApiIgnore
@RestController
public class CustomErrorController implements ErrorController {

    /**
     * 에러 API 반환
     */
    @RequestMapping("${server.error.path:${error.path:/error}}")
    @SuppressWarnings("rawtypes")
    public InnerResponseDto error(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errMsg = (String)request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        String requestUri = (String)request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        log.error(">>> statusCode: {}, errMsg: {}, requestUri: {}", statusCode, errMsg, requestUri);

        InnerResponseCodeType innerResponseCodeType;
        if (HttpStatus.NOT_FOUND.value() == statusCode) {
            innerResponseCodeType = InnerResponseCodeType.NOT_FOUND;
        }
        else {
            innerResponseCodeType = InnerResponseCodeType.INTERNAL_SERVER_ERROR;
        }

        return InnerResponseDto.of(innerResponseCodeType);
    }

}
