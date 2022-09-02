package com.lguplus.fleta.exception.study.member;

public class InvalidMemberInfoException extends MemberException {

    public InvalidMemberInfoException(String message) {
        super(message);
    }

    public InvalidMemberInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMemberInfoException(Throwable cause) {
        super(cause);
    }
}
