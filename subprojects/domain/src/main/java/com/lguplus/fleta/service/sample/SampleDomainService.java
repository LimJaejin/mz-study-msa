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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
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
    public List<SampleDto> getSamplesByCondition(SampleQueryConditonDto queryConditonDto) {
        try {
            // 검색 조건 체크
//            this.checkQueryCondition(queryConditonDto);

            // entity -> dto 변환 및 dto 속성 값 변환 처리
            return this.sampleRepository.getSamples(queryConditonDto).stream()
                    .map(this.sampleMapper::toDto)
                    .peek(s -> {
                        s.setRegWeekday(this.extractWeekday(s.getRegDate()));
                        this.changeNameAndEmail(s);
                    })
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new ServiceException(OuterResponseType.FAIL_004, e);
        }
    }

    /**
     * entity를 직접 set하면 안되는 이유
     *
     * @return
     */
    public List<SampleMemberDto> getAllMembers() {
        try {
            List<SampleMember> sampleMembers = this.sampleRepository.getAllMembers();
            // sampleMembers.clear(); // 전체 삭제안됨.
            // sampleMembers.remove(0); // 개별 삭제 안됨.

            // entity -> dto 변환 및 dto 속성 값 변환 처리
            return sampleMembers.stream()
                    .peek(s -> s.setUpdDate(LocalDateTime.now()))
                    .map(this.sampleMemberMapper::toDto)
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

    /**
     * 등록일시의 요일 추출(db function을 메소드로 변경한 부분)
     *
     * @param datetime LocalDateTime
     * @return String 월/화/수/목/금/토/일 중 하나
     */
    private String extractWeekday(LocalDateTime datetime) {
        if (Objects.isNull(datetime)) {
            return null;
        }

        LocalDate date = datetime.toLocalDate();
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN);
    }

    /**
     * 이름 및 이메일 마스킹 처리
     *
     * @param dto SampleDto
     */
    private void changeNameAndEmail(SampleDto dto) {
        String name = dto.getName();
        dto.setName(StringUtils.left(name, 2) + "X");

        String email = dto.getEmail();
        String[] arr = email.split("@", -1);
        dto.setEmail(StringUtils.left(arr[0], 3) + "XXX@" + arr[1]);
    }

}
