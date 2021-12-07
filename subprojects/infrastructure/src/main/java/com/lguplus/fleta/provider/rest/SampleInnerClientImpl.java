package com.lguplus.fleta.provider.rest;

import com.lguplus.fleta.client.SampleInnerClient;
import com.lguplus.fleta.data.dto.response.InnerResponseDto;
import com.lguplus.fleta.data.dto.response.InnerResponseResultDto;
import com.lguplus.fleta.data.dto.sample.SampleInnerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SampleInnerClientImpl implements SampleInnerClient {

    private final SampleFeignClient sampleFeignClient;

    @Override
    public String getInnerHttpTest() {
        InnerResponseDto<SampleInnerDto> response = sampleFeignClient.getInnerHttpTest();
        log.debug(">>> response : {}", response);
        InnerResponseResultDto<SampleInnerDto> result = response.getResult();
        return result.getData().getName();
    }
}
