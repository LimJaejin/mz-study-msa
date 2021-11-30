package com.lguplus.fleta.dto.sample;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SampleHeaderDto {

    private String flag;
    private String message;

    public static SampleHeaderDto makeHeader(String flag , String message) {
        return SampleHeaderDto.builder()
                .flag(flag)
                .message(message)
                .build();
    }

}
