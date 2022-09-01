package com.lguplus.fleta.exception.study.member;

public class DuplicateMemberEmailException extends MemberException {

    public DuplicateMemberEmailException(Throwable e) {
        super(e);
    }

    public DuplicateMemberEmailException(String errorMessage) {
        super(errorMessage);
    }

    public DuplicateMemberEmailException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }
}
