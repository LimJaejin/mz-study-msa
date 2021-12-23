package com.lguplus.fleta.data.entity.custom.sample;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class SampleEntityDto implements Serializable {

    @Id
    private String id;

    private String name;

    private String email;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;

    @Column(name = "mapp_id")
    private String mappId;

    @Column(name = "sa_id")
    private String saId;

    @Column(name = "stb_mac")
    private String stbMac;

    @Column(name = "mapping_date")
    private String mappingDate;

}
