package com.lguplus.fleta.provider.rest;

import com.lguplus.fleta.client.SubscriberInfoClient;
import com.lguplus.fleta.data.dto.common.subscriber.SubscriberInfoDto;
import com.lguplus.fleta.data.dto.response.InnerResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SubscriberInfoClientImpl implements SubscriberInfoClient {

    private final SubscriberFeignClient subscriberFeignClient;

    @Override
    public InnerResponseDto<SubscriberInfoDto> findBySaIdAndStbMac(String saId, String stbMac) {
        return this.subscriberFeignClient.getSubscriberInfo(saId, stbMac);
    }

}
