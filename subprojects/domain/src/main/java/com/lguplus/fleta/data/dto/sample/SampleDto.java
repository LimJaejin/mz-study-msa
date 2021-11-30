package com.lguplus.fleta.data.dto.sample;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class SampleDto implements Serializable {

    private String id;

    private String name;

    private String email;

    private LocalDateTime regDate;

    private String regWeekday;

    private LocalDateTime updDate;

    private String mappId;

    private String saId;

    private String stbMac;

    private String mappingDate;

}
