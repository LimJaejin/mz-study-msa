package com.lguplus.fleta.exception;

import com.lguplus.fleta.data.dto.response.CommonResponseDto;
import com.lguplus.fleta.data.type.response.OuterResponseType;
import lombok.Getter;

public class ServiceException extends RuntimeException {

    @Getter
    private OuterResponseType outerResponseType;

    @Getter
    private String errorMessage;

    @Getter
    private CommonResponseDto responseDto;

    public ServiceException(OuterResponseType outerResponseType, Throwable e) {
        super(e);
        this.outerResponseType = outerResponseType;
    }

    public ServiceException(String errorMessage, Throwable e) {
        super(e);
        this.errorMessage = errorMessage;
    }

    public ServiceException(CommonResponseDto responseDto, Throwable e) {
        super(e);
        this.responseDto = responseDto;
    }

    public ServiceException(OuterResponseType outerResponseType) {
        this.outerResponseType = outerResponseType;
    }

    public ServiceException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
