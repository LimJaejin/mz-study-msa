package com.lguplus.fleta.data.mapper;

import com.lguplus.fleta.config.MapStructConfig;
import com.lguplus.fleta.data.dto.sample.SampleMemberDomainDto;
import com.lguplus.fleta.data.vo.SampleMemberVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapStructConfig.class)
public interface SampleMemberMapper {

    @Mappings({
        @Mapping(source = "userName", target = "name"),
        @Mapping(source = "userEmail", target = "email")
    })
    SampleMemberVo toVo(SampleMemberDomainDto memberDto);

    @Mappings({
        @Mapping(source = "name", target = "userName"),
        @Mapping(source = "email", target = "userEmail")
    })
    SampleMemberDomainDto toDto(SampleMemberVo memberVo);
}
