package com.lguplus.fleta.data.type.response;

public enum OuterResponseType {

    SUCCESS("0", "성공"),
    FAIL_001("1", "가입자 정보 조회 실패"),
    FAIL_002("1", "샘플 멤버 등록 실패"),
    FAIL_003("1", "샘플 멤버 매핑 등록 실패"),
    FAIL_004("1", "샘플 멤버 목록 조회 실패");

    private final String code;
    private final String message;

    OuterResponseType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

}
