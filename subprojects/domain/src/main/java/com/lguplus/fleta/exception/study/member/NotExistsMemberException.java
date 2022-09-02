package com.lguplus.fleta.exception.study.member;

public class NotExistsMemberException extends MemberException {

    public NotExistsMemberException(Throwable e) {
        super(e);
    }

    public NotExistsMemberException(String errorMessage) {
        super(errorMessage);
    }

    public NotExistsMemberException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }
}
