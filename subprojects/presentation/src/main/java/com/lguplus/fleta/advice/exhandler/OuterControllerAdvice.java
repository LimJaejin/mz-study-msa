package com.lguplus.fleta.advice.exhandler;

import com.lguplus.fleta.exception.ParameterValidateException;
import com.lguplus.fleta.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice("com.lguplus.fleta.api.outer")
public class OuterControllerAdvice {

    /**
     * 잘못된 파라미터 Validate 발견시 호출
     * @param ex
     * @return
     */
    @ExceptionHandler({ParameterValidateException.class})
    public ResponseEntity<Object> parameterValidateException(final ParameterValidateException ex) {
        // 잘못된 파라미터는 서버 오류 아님. 그래서 badRequest() 사용하면 안됨
        return ResponseEntity.ok().body(ex.getMessage());
    }

    /**
     * 기존 IMCS 는 서버 오류 발생시 공백을 리턴함.
     * 추후 리턴값은 바뀔 수 있음.
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> globalException(final Exception ex) {
        log.error("globalException", ex);
        return ResponseEntity.ok().body("");
    }

    // application 레이어에서 throw한 오류는 controller를 거쳐 여기서 처리된다.
    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<?> handleServiceException(HttpServletRequest req, final ServiceException ex) {
        log.error(req.getRequestURL() + " ServiceException: ", ex);
        // return ResponseEntity.ok().body(ex.getResponseDto().serializeErrorMessage());
        return ResponseEntity.ok().body(ex.getMessage());
    }

}
