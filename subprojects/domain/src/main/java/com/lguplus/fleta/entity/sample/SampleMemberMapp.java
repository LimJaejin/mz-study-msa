package com.lguplus.fleta.entity.sample;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "imcsuser.sample_member_mapp")
public class SampleMemberMapp implements Serializable {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(name = "mapp_id")
    private String mappId;

    @Column(name = "sample_member_id", nullable = false)
    private String sampleMemberId;

    @Column(name = "sa_id", nullable = false)
    private String saId;

    @Column(name = "stb_mac", nullable = false)
    private String stbMac;

    @Column(name = "mapping_date", nullable = false)
    private LocalDateTime mappingDate;

}
