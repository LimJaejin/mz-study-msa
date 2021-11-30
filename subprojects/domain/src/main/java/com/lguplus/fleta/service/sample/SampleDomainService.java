package com.lguplus.fleta.service.sample;

import com.lguplus.fleta.data.dto.sample.SampleDto;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import com.lguplus.fleta.data.dto.sample.SampleMemberMappDto;
import com.lguplus.fleta.data.dto.sample.SampleQueryConditonDto;
import com.lguplus.fleta.data.entity.sample.SampleMember;
import com.lguplus.fleta.data.entity.sample.SampleMemberMapp;
import com.lguplus.fleta.data.mapper.sample.SampleMapper;
import com.lguplus.fleta.data.mapper.sample.SampleMemberMappMapper;
import com.lguplus.fleta.data.mapper.sample.SampleMemberMapper;
import com.lguplus.fleta.data.type.response.OuterResponseType;
import com.lguplus.fleta.exception.ServiceException;
import com.lguplus.fleta.repository.sample.SampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class SampleDomainService {

    private final SampleMapper sampleMapper;
    private final SampleMemberMapper sampleMemberMapper;
    private final SampleMemberMappMapper sampleMemberMappMapper;
    private final SampleRepository sampleRepository;

    /**
     * 샘플 멤버를 등록한다.
     *
     * @param dto 샘플 멤버 Dto
     * @return SampleMemberDto
     */
    public SampleMemberDto create(SampleMemberDto dto) {
        try {
            dto.setRegDate(LocalDateTime.now());

            // dto -> entity 변환
            SampleMember entity = this.sampleMemberMapper.toEntity(dto);

            // entity DB 저장
            this.sampleRepository.create(entity);

            // entity -> dto 변환
            return this.sampleMemberMapper.toDto(entity);
        }
        catch (Exception e) {
            throw new ServiceException(OuterResponseType.FAIL_002, e);
        }
    }

    /**
     * 샘플 멤버 매핑 데이터를 등록한다.
     *
     * @param sampleMemberId 샘플 멤버 아이디
     * @param mappDto 샘플 멤버 매핑 Dto
     * @return SampleMemberMappDto
     */
    public SampleMemberMappDto create(String sampleMemberId, SampleMemberMappDto mappDto) {
        try {
            mappDto.setSampleMemberId(sampleMemberId);
            mappDto.setMappingDate(LocalDateTime.now());

            SampleMemberMapp entity = this.sampleMemberMappMapper.toEntity(mappDto);

            this.sampleRepository.create(entity);

            return this.sampleMemberMappMapper.toDto(entity);
        }
        catch (Exception e) {
            throw new ServiceException(OuterResponseType.FAIL_003, e);
        }
    }

    /**
     * 샘플 멤버 & 샘플 멤버 매핑 목록을 조회한다.
     *
     * @param queryConditonDto 쿼리 검색 조건 Dto
     * @return List<SampleDto>
     */
    public List<SampleDto> findSamplesByCondition(SampleQueryConditonDto queryConditonDto) {
        try {
            // 검색 조건 체크
            this.checkQueryCondition(queryConditonDto);

            // entity -> dto 변환 및 dto 속성 값 변환 처리
            return this.sampleRepository.findSamples(queryConditonDto).stream()
                    .map(this.sampleMapper::toDto)
                    .peek(s -> {
                        String name = s.getName();
                        s.setName(StringUtils.left(name, 2) + "X");

                        String email = s.getEmail();
                        String[] arr = email.split("@", -1);
                        s.setEmail(StringUtils.left(arr[0], 3) + "XXX@" + arr[1]);
                    })
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new ServiceException(OuterResponseType.FAIL_004, e);
        }
    }

    /**
     * 검색 조건 체크
     *
     * @param queryConditonDto
     */
    private void checkQueryCondition(SampleQueryConditonDto queryConditonDto) {
        if (Objects.isNull(queryConditonDto)
                || (
                StringUtils.isBlank(queryConditonDto.getName())
                        && StringUtils.isBlank(queryConditonDto.getEmail())
                        && StringUtils.isBlank(queryConditonDto.getSaId())
                        && StringUtils.isBlank(queryConditonDto.getStbMac())
            )
        ) {
            throw new ServiceException("검색 조건없는 조회는 불가합니다. 검색 조건을 입력해주세요.");
        }
    }

}
