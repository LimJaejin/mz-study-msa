package com.lguplus.fleta.service;

import com.lguplus.fleta.data.dto.sample.*;
import com.lguplus.fleta.data.type.response.OuterResponseType;
import com.lguplus.fleta.dto.sample.SampleResponseDto;
import com.lguplus.fleta.exception.ServiceException;
import com.lguplus.fleta.service.common.SubscriberDomainService;
import com.lguplus.fleta.service.sample.SampleDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SampleService {

    private final SubscriberDomainService subscriberDomainService;
    private final SampleDomainService sampleDomainService;

    /**
     * 샘플 멤버 & 샘플 멤버 매핑 데이터를 등록한다.
     *
     * @param dto SampleMemberDto
     * @param mappDto SampleMemberMappDto
     * @return String
     */
    @Transactional(rollbackFor = { Exception.class })
    public String create(SampleMemberDto dto, SampleMemberMappDto mappDto) {
        try {
            this.subscriberDomainService.getSubscriberOrCache(mappDto.getSaId(), mappDto.getStbMac());

            SampleMemberDto sampleMemberDto = this.sampleDomainService.create(dto);
            this.sampleDomainService.create(sampleMemberDto.getId(), mappDto);

//            this.throwException(true);

            return SampleResponseDto.serialize(OuterResponseType.SUCCESS);
        }
        catch (ServiceException e) {
            this.subscriberDomainService.deleteSubscriberCache(mappDto.getSaId(), mappDto.getStbMac());
            throw new ServiceException(new SampleResponseDto(e.getOuterResponseType()), e);
        }
    }

    /**
     * 샘플 멤버 & 샘플 멤버 매핑 목록을 조회한다.
     *
     * @param queryConditonDto SampleQueryConditonDto
     * @return String
     */
    public String findSamples(SampleQueryConditonDto queryConditonDto) {
        try {
            List<SampleDto> samples = this.sampleDomainService.findSamplesByCondition(queryConditonDto);

            return SampleResponseDto.serialize(OuterResponseType.SUCCESS, samples);
        }
        catch (ServiceException e) {
            throw new ServiceException(new SampleResponseDto(e.getOuterResponseType()), e);
        }
    }

    public String badQueryThrowEx(SampleQueryConditonDto queryConditonDto) {
        try {
            // 4 test
            queryConditonDto.setName("나쁜쿼리");

            List<SampleDto> samples = this.sampleDomainService.findSamplesByCondition(queryConditonDto);

            return SampleResponseDto.serialize(OuterResponseType.SUCCESS, samples);
        }
        catch (ServiceException e) {
            throw new ServiceException(new SampleResponseDto(e.getOuterResponseType()), e);
        }
    }

    public String badQueryCatchEx(SampleQueryConditonDto queryConditonDto) {
        try {
            // 4 test
            queryConditonDto.setName("나쁜쿼리");

            List<SampleDto> samples = this.sampleDomainService.findSamplesByCondition(queryConditonDto);

            return SampleResponseDto.serialize(OuterResponseType.SUCCESS, samples);
        }
        catch (ServiceException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    private void throwException(boolean tf) {
        if (tf) {
            throw new ServiceException(OuterResponseType.FAIL_001);
        }
    }

}
