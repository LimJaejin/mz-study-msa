package com.lguplus.fleta.exception.study.member;

public class DuplicateMemberEmailException extends MemberException {

    public DuplicateMemberEmailException(String message) {
        super(message);
    }

    public DuplicateMemberEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateMemberEmailException(Throwable cause) {
        super(cause);
    }
}
