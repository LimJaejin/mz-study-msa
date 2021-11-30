package com.lguplus.fleta.repository.sample;

import com.lguplus.fleta.data.dto.sample.SampleQueryConditonDto;
import com.lguplus.fleta.data.entity.custom.sample.SampleEntityDto;
import com.lguplus.fleta.data.entity.sample.SampleMember;
import com.lguplus.fleta.data.entity.sample.SampleMemberMapp;

import java.util.List;

public interface SampleRepository {

    SampleMember create(SampleMember entity);

    SampleMemberMapp create(SampleMemberMapp entity);

    List<SampleEntityDto> getSamples(SampleQueryConditonDto queryConditonDto);

}
