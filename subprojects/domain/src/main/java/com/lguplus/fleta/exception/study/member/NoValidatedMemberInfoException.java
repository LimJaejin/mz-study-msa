package com.lguplus.fleta.exception.study.member;

public class NoValidatedMemberInfoException extends MemberException {

    public NoValidatedMemberInfoException(Throwable e) {
        super(e);
    }

    public NoValidatedMemberInfoException(String errorMessage) {
        super(errorMessage);
    }

    public NoValidatedMemberInfoException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }
}
