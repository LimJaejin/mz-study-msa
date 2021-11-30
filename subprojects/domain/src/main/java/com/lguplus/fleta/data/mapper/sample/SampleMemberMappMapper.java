package com.lguplus.fleta.data.mapper.sample;

import com.lguplus.fleta.config.MapStructConfig;
import com.lguplus.fleta.data.dto.sample.SampleMemberMappDto;
import com.lguplus.fleta.data.entity.sample.SampleMemberMapp;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface SampleMemberMappMapper {

    SampleMemberMapp toEntity(SampleMemberMappDto dto);

    SampleMemberMappDto toDto(SampleMemberMapp entity);

}
