package com.lguplus.fleta.data.entity.study;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "study_member", schema = "public")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer id;

    private String name;
    private String email;

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
