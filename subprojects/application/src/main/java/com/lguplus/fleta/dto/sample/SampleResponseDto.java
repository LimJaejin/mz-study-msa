package com.lguplus.fleta.dto.sample;

import com.lguplus.fleta.data.dto.response.CommonResponseDto;
import com.lguplus.fleta.data.dto.sample.SampleDto;
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

    private static final String ROWSEP		= "\f";		// 행 분리자
    private static final String COLSEP		= "|";		// 열 분리자

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
            sb.append(StringUtils.defaultString(responseDto.getSampleHeaderDto().getFlag(), StringUtils.EMPTY)).append(COLSEP);
            sb.append(StringUtils.defaultString(responseDto.getSampleHeaderDto().getMessage(), StringUtils.EMPTY)).append(COLSEP);
            sb.append(ROWSEP); // 행분리자
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
                sb.append(StringUtils.defaultString(s.getName(), StringUtils.EMPTY)).append(COLSEP);
                sb.append(StringUtils.defaultString(s.getEmail(), StringUtils.EMPTY)).append(COLSEP);
                sb.append(StringUtils.defaultString(s.getRegWeekday(), StringUtils.EMPTY)).append(COLSEP);
                sb.append(ROWSEP); // 행분리자
            });
        }

        return result + sb;
    }

    @Override
    public String serializeErrorMessage() {
        return serialize(this.outerResponseType);
    }

}
