package com.lguplus.fleta.data.vo.sample;

import com.lguplus.fleta.data.dto.sample.SampleQueryConditonDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SampleQueryParamsVo extends SampleQueryConditonDto {

    public SampleQueryConditonDto toDto() {
        return SampleQueryConditonDto.builder()
                .name(super.getName())
                .email(super.getEmail())
                .saId(super.getSaId())
                .stbMac(super.getStbMac())
                .build();
    }

}
