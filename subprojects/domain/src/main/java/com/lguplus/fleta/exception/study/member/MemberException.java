package com.lguplus.fleta.exception.study.member;

import com.lguplus.fleta.exception.ServiceException;

public class MemberException extends ServiceException {

    public MemberException(Throwable e) {
        super(e);
    }

    public MemberException(String errorMessage) {
        super(errorMessage);
    }

    public MemberException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }
}
