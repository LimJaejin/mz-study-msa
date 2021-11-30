package com.lguplus.fleta.provider.jpa.sample;

import com.lguplus.fleta.data.dto.sample.SampleQueryConditonDto;
import com.lguplus.fleta.data.entity.custom.sample.SampleEntityDto;
import com.lguplus.fleta.data.entity.sample.SampleMember;
import com.lguplus.fleta.data.entity.sample.SampleMemberMapp;
import com.lguplus.fleta.repository.sample.SampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 도메인 레이어의 SampleRepository 인터페이스 구현 클래스
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class SampleRepositoryImpl implements SampleRepository {

    private final SampleMemberJpaRepository sampleMemberJpaRepository;
    private final SampleMemberMappJpaRepository sampleMemberMappJpaRepository;
    private final SampleEmRepository sampleEmRepository;

    @Override
    public SampleMember create(SampleMember entity) {
        return this.sampleMemberJpaRepository.save(entity);
    }

    @Override
    public SampleMemberMapp create(SampleMemberMapp entity) {
        return this.sampleMemberMappJpaRepository.save(entity);
    }

    @Override
    public List<SampleEntityDto> getSamples(SampleQueryConditonDto queryConditonDto) {
        return this.sampleEmRepository.findSamples(queryConditonDto);
    }

}
