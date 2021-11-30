package com.lguplus.fleta.data.mapper;

import com.lguplus.fleta.config.ObjectMapperConfig;
import com.lguplus.fleta.data.dto.sample.SampleMemberDomainDto;
import com.lguplus.fleta.data.vo.SampleMemberVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ObjectMapperConfig.class)
public interface SampleMemberMapper {

    @Mapping(source = "userName", target = "name")
    @Mapping(source = "userEmail", target = "email")
    SampleMemberVo toVo(SampleMemberDomainDto memberDto);

    @Mapping(source = "name", target = "userName")
    @Mapping(source = "email", target = "userEmail")
    SampleMemberDomainDto toDto(SampleMemberVo memberVo);
}
