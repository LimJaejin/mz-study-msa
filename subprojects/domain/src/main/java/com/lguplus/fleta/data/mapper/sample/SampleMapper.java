package com.lguplus.fleta.data.mapper.sample;

import com.lguplus.fleta.config.MapStructConfig;
import com.lguplus.fleta.data.dto.sample.SampleDto;
import com.lguplus.fleta.data.entity.custom.sample.SampleEntityDto;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface SampleMapper {

    SampleDto toDto(SampleEntityDto entity);

}
