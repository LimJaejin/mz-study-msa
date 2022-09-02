package com.lguplus.fleta.exception.study.member;

import com.lguplus.fleta.exception.ServiceException;

public class MemberException extends ServiceException {

    public MemberException(String message) {
        super(message);
    }

    public MemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberException(Throwable cause) {
        super(cause);
    }
}
