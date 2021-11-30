package com.lguplus.fleta.data.mapper;

import com.lguplus.fleta.config.ObjectMapperConfig;
import com.lguplus.fleta.data.dto.sample.SampleCustomMemberDto;
import com.lguplus.fleta.data.entity.SampleMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ObjectMapperConfig.class)
public interface SampleCustomMemberMapper {

    @Mapping(target = "memberId", expression = "java(member.getId())")
    @Mapping(target = "memberName", expression = "java(member.getName())")
    @Mapping(target = "teamName", expression = "java(member.getTeam().getName())")
    SampleCustomMemberDto toDto(SampleMember member);
}
