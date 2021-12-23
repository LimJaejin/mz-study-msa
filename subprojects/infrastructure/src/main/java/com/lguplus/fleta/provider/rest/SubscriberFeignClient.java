package com.lguplus.fleta.provider.rest;

import com.lguplus.fleta.data.dto.common.subscriber.SubscriberInfoDto;
import com.lguplus.fleta.data.dto.response.InnerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "subscriber-feign", url = "${service.subscriber.url}")
public interface SubscriberFeignClient {

    @GetMapping("/subscriber/subscribers")
    InnerResponseDto<SubscriberInfoDto> getSubscriberInfo(@RequestParam("saId") String saId, @RequestParam("stbMac") String stbMac);

}
