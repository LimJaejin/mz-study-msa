package com.lguplus.fleta.data.dto.sample;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SampleQueryConditonDto implements Serializable {

    private String name;

    private String email;

    private String saId;

    private String stbMac;

}
