package com.lguplus.fleta.data.dto.study;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberSearchCond {

    private String name;
    private String email;
}
