package com.lguplus.fleta.data.entity.custom;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SampleCusomMemberEntityDto {

    @Id
    private int memberId;

    private String memberName;

    private String teamName;
}
