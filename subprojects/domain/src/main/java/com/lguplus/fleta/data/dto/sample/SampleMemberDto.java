package com.lguplus.fleta.data.dto.sample;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SampleMemberDto implements Serializable {

    private String id;

    private String name;

    private String email;

    private LocalDateTime regDate;

    private LocalDateTime updDate;

    public SampleMemberDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
