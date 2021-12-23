package com.lguplus.fleta.data.mapper.sample;

import com.lguplus.fleta.config.MapStructConfig;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import com.lguplus.fleta.data.entity.sample.SampleMember;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface SampleMemberMapper {

    SampleMember toEntity(SampleMemberDto dto);

    SampleMemberDto toDto(SampleMember entity);

}
