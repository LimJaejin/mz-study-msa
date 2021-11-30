package com.lguplus.fleta.data.dto.sample;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SampleMemberMappDto implements Serializable {

    private String mappId;

    private String sampleMemberId;

    private String saId;

    private String stbMac;

    private LocalDateTime mappingDate;

    public SampleMemberMappDto(String saId, String stbMac) {
        this.saId = saId;
        this.stbMac = stbMac;
    }

}
