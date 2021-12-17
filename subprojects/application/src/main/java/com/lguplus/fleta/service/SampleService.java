package com.lguplus.fleta.service;

import com.lguplus.fleta.data.dto.sample.*;
import com.lguplus.fleta.data.type.response.OuterResponseType;
import com.lguplus.fleta.dto.InnerEvent;
import com.lguplus.fleta.dto.sample.SampleResponseDto;
import com.lguplus.fleta.exception.ServiceException;
import com.lguplus.fleta.service.common.SubscriberDomainService;
import com.lguplus.fleta.service.sample.SampleDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SampleService {

    private final SubscriberDomainService subscriberDomainService;
    private final SampleDomainService sampleDomainService;

    private final ApplicationEventPublisher eventPublisher;

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

            this.eventPublisher.publishEvent(new InnerEvent<>("sample-inserted", sampleMemberDto));

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
    public String getSamples(SampleQueryConditonDto queryConditonDto) {
        try {
            List<SampleDto> samples = this.sampleDomainService.getSamplesByCondition(queryConditonDto);

            return SampleResponseDto.serialize(OuterResponseType.SUCCESS, samples);
        }
        catch (ServiceException e) {
            throw new ServiceException(new SampleResponseDto(e.getOuterResponseType()), e);
        }
    }

    /**
     * sql 문법 오류 시 익셉션 throw 테스트
     * 결과: 의도한 오류 메시지 응답 -> "1|샘플 멤버 목록 조회 실패|"
     *
     * @param queryConditonDto
     * @return
     */
    public String badQueryThrowEx(SampleQueryConditonDto queryConditonDto) {
        try {
            // 4 test
            queryConditonDto.setName("나쁜쿼리");

            List<SampleDto> samples = this.sampleDomainService.getSamplesByCondition(queryConditonDto);

            return SampleResponseDto.serialize(OuterResponseType.SUCCESS, samples);
        }
        catch (ServiceException e) {
            throw new ServiceException(new SampleResponseDto(e.getOuterResponseType()), e);
        }
    }

    /**
     * sql 문법 오류 시 익셉션 catch 테스트
     * 결과: 의도하지 않은 globalException 발생 -> org.springframework.transaction.UnexpectedRollbackException: Transaction silently rolled back because it has been marked as rollback-only
     *
     * @param queryConditonDto
     * @return
     */
    public String badQueryCatchEx(SampleQueryConditonDto queryConditonDto) {
        try {
            // 4 test
            queryConditonDto.setName("나쁜쿼리");

            List<SampleDto> samples = this.sampleDomainService.getSamplesByCondition(queryConditonDto);

            return SampleResponseDto.serialize(OuterResponseType.SUCCESS, samples);
        }
        catch (Exception e) {
            // 트랜잭션 내에서 오류를 catch한다는 것은 commit을 한다는 의미이다.
            log.error(e.getMessage());
        }

        return null;
    }

    /**
     * sql 문법 오류가 발생하는 쿼리와 정상 쿼리를 함께 수행하는 상황
     *
     * @param queryConditonDto
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public String complexEx(SampleQueryConditonDto queryConditonDto) {
        List<SampleDto> samples = new ArrayList<>();

        log.debug(">>> 트랜잭션 활성 상태: {}", TransactionSynchronizationManager.isActualTransactionActive());

        try {
            // sql 문법 오류가 발생하는 쿼리
            queryConditonDto.setName("나쁜쿼리");
            samples.addAll(this.sampleDomainService.getSamplesByCondition(queryConditonDto));
        }
        catch (Exception e) {
            // 오류는 꼭 로그로 남긴다.
            log.error(e.getMessage());
        }

        log.debug(">>> 트랜잭션 활성 상태: {}", TransactionSynchronizationManager.isActualTransactionActive());

        try {
            // 정상적으로 수행되는 쿼리
            queryConditonDto.setName(null);
            samples.addAll(this.sampleDomainService.getSamplesByCondition(queryConditonDto));
        }
        catch (Exception e) {
            // 오류는 꼭 로그로 남긴다.
            log.error(e.getMessage());
        }

        return SampleResponseDto.serialize(OuterResponseType.SUCCESS, samples);
    }

    @Transactional
    public String getAllMembers() {
        try {
            this.sampleDomainService.getAllMembers();

            return SampleResponseDto.serialize(OuterResponseType.SUCCESS);
        }
        catch (ServiceException e) {
            throw new ServiceException(new SampleResponseDto(e.getOuterResponseType()), e);
        }
    }

}
