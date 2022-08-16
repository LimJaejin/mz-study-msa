Application Layer
===
## 개요
+ 고수준으로 추상화 된 어플리케이션 요구사항 처리
+ API별 추상화 된 비즈니스 로직 흐름 정의
+ 상세 기준
  + 최대한 시퀀스 다이어그램의 스텝당 하나의 Domain 레이어 메소드 호출 블록으로 표현
  + 각 Domain 레이어 메소드 호출 블록 위에 시퀀스 다이어그램에 정의된 번호와 간략한 내용을 주석으로 표시
  + 아래의 경우, 예외적으로 여러 스텝을 하나의 Domain 레이어 메소드로 표현 가능
    + 여러 스텝이 응집도가 강한 하나의 비즈니스 로직을 의미하는 경우
    + 여러 스텝을 하나의 캐시 단위로 묶어야 하는 경우
  + 여러 스텝을 하나의 메소드로 묶어야 하는 경우, Application 레이어에 ServiceStep 클래스를 별도로 만들어 사용
+ 트랜잭션, 스케줄링 동시실행 제어 (ShedLock, Redisson 등)
___ 
## 주요 클래스들의 역할
### Service
+ 유스케이스에 해당하며, domain 레이어의 서비스(들)를 호출합니다.
+ 트랜잭션을 관리합니다.
```
package com.lguplus.fleta.service;

import com.lguplus.fleta.data.dto.sample.SampleDto;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import com.lguplus.fleta.data.dto.sample.SampleMemberMappDto;
import com.lguplus.fleta.data.dto.sample.SampleResponseDto;
import com.lguplus.fleta.data.dto.sample.SampleQueryConditonDto;
import com.lguplus.fleta.data.type.response.OuterResponseType;
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

            return SampleResponseDto.serialize(OuterResponseType.SUCCESS);
        }
        catch (ServiceException e) {
            // 오류 발생 시 캐쉬를 삭제할 필요가 있을 경우에만 실행하도록 처리합니다.
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
            List<SampleDto> samples = this.sampleDomainService.getSamples(queryConditonDto);

            return SampleResponseDto.serialize(OuterResponseType.SUCCESS, samples);
        }
        catch (ServiceException e) {
            throw new ServiceException(new SampleResponseDto(e.getOuterResponseType()), e);
        }
    }

}
```
___
### ResponseDto
+ 성공 또는 실패 시 응답 메시지를 구성합니다.
+ CommonResponseDto<T> 인터페이스를 구현합니다.
```
package com.lguplus.fleta.data.dto.sample;

import com.lguplus.fleta.data.constant.ImcsConstants;
import com.lguplus.fleta.data.dto.response.CommonResponseDto;
import com.lguplus.fleta.data.type.response.OuterResponseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SampleResponseDto implements CommonResponseDto<String> {

    private SampleHeaderDto sampleHeaderDto;

    private OuterResponseType outerResponseType;

    public SampleResponseDto(OuterResponseType outerResponseType) {
        this.outerResponseType = outerResponseType;
    }

    /**
     * 최종 Response 결과 생성
     *
     * @param outerResponseType 응답 타입
     * @return SampleResponseDto
     */
    private static SampleResponseDto create(OuterResponseType outerResponseType) {
        return SampleResponseDto.builder()
                .sampleHeaderDto(SampleHeaderDto.makeHeader(outerResponseType.code() , outerResponseType.message()))
                .build();
    }

    /**
     * 최종 결과 생성
     *
     * @param outerResponseType OuterResponseType
     * @return String 응답 결과 문자열
     */
    public static String serialize(OuterResponseType outerResponseType) {
        SampleResponseDto responseDto = create(outerResponseType);

        StringBuilder sb = new StringBuilder();

        if (responseDto.getSampleHeaderDto() != null) {
            sb.append(StringUtils.defaultString(responseDto.getSampleHeaderDto().getFlag(), "")).append(ImcsConstants.COLSEP);
            sb.append(StringUtils.defaultString(responseDto.getSampleHeaderDto().getMessage(), "")).append(ImcsConstants.COLSEP);
            sb.append(ImcsConstants.ROWSEP); // 행분리자
        }

        return sb.toString();
    }

    /**
     * 최종 결과 생성
     *
     * @param outerResponseType OuterResponseType
     * @param samples 샘플 멤버 & 샘플 멤버 매핑 목록
     * @return String 응답 결과 문자열
     */
    public static String serialize(OuterResponseType outerResponseType, List<SampleDto> samples) {
        String result = serialize(outerResponseType);

        StringBuilder sb = new StringBuilder();

        if (samples != null && !samples.isEmpty()) {
            samples.forEach(s -> {
                sb.append(StringUtils.defaultString(s.getName(), "")).append(ImcsConstants.COLSEP);
                sb.append(StringUtils.defaultString(s.getEmail(), "")).append(ImcsConstants.COLSEP);
                sb.append(ImcsConstants.ROWSEP); // 행분리자
            });
        }

        return result + sb;
    }

    @Override
    public String serializeErrorMessage() {
        return serialize(this.outerResponseType);
    }

}
```
___
## 일반적인 application 레이어 개발
+ domain 레이어의 비즈니스 로직을 조합합니다.
  + if 문 등 사소한 분기 처리라도 가능하다면 domain 레이어 내에서 처리하도록 합니다.
+ 트랜잭션 관리는 아래와 같이 처리합니다.
  + 메소드 상단에 @Transactional(readOnly = true) 선언합니다.
  + 메소드 내부에서 하나 이상의 CUD가 발생한다면 @Transactional 어노테이션을 선언합니다.
+ domain 및 infrastructure 레이어에서 발생한 오류를 최종 클라이언트에게 보낼 메시지로 구성 후, 다시 custom exception으로 throw합니다.