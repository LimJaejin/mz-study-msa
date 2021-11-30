package com.lguplus.fleta.service;

import com.lguplus.fleta.data.dto.common.subscriber.SubscriberInfoDto;
import com.lguplus.fleta.service.common.SubscriberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SubscriberInfoService {

    private final SubscriberDomainService subscriberDomainService;

    /**
     * 가입자 정보 조회하기(subscriber-service API 연동)
     * @param saId
     * @param stbMac
     * @return
     */
    public SubscriberInfoDto getSubscriberInfo(String saId, String stbMac) {
        return subscriberDomainService.getSubscriberOrCache(saId, stbMac);
    }

}
