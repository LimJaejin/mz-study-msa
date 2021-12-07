package com.lguplus.fleta.provider.rest;

import com.lguplus.fleta.data.dto.response.InnerResponseDto;
import com.lguplus.fleta.data.dto.sample.SampleInnerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "basic-client", url = "${service.msa-boilerplate.url}")
public interface SampleFeignClient {

    @GetMapping("/msa-boilerplate/inner/test")
    InnerResponseDto<SampleInnerDto> getInnerHttpTest();
}
