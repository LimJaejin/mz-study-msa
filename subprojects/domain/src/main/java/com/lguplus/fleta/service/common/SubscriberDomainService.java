package com.lguplus.fleta.service.common;

import com.lguplus.fleta.client.SubscriberInfoClient;
import com.lguplus.fleta.data.dto.common.subscriber.SubscriberInfoDto;
import com.lguplus.fleta.data.dto.response.InnerResponseDto;
import com.lguplus.fleta.data.dto.response.InnerResponseResultDto;
import com.lguplus.fleta.data.type.CacheNameType;
import com.lguplus.fleta.data.type.response.OuterResponseType;
import com.lguplus.fleta.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class SubscriberDomainService {

    private final SubscriberInfoClient subscriberInfoClient;

    /**
     * 가입자 정보를 조회 후 캐쉬에 저장한다.
     *
     * @param saId
     * @param stbMac
     * @return
     */
    @Cacheable(cacheNames = CacheNameType.TTS_60
            , key="'SubscriberDomainService.findSubscriber::' + #saId + '::' + #stbMac"
            , unless = "#result == null"
    )
    public SubscriberInfoDto getSubscriberOrCache(String saId, String stbMac) {
        InnerResponseResultDto<SubscriberInfoDto> result;

        try {
            InnerResponseDto<SubscriberInfoDto> response = this.subscriberInfoClient.findBySaIdAndStbMac(saId, stbMac);
            result = response.getResult();
        }
        catch (Exception e) {
            throw new ServiceException(OuterResponseType.FAIL_001, e);
        }

        if (Objects.isNull(result.getData())) {
            throw new ServiceException(OuterResponseType.FAIL_001);
        }

        return result.getData();
    }

    /**
     * 가입자 정보 캐쉬를 삭제한다.
     *
     * @param saId
     * @param stbMac
     * @return
     */
    @CacheEvict(cacheNames = CacheNameType.TTS_60
            , key="'SubscriberDomainService.findSubscriber::' + #saId + '::' + #stbMac"
    )
    public void deleteSubscriberCache(String saId, String stbMac) {
        log.info(">>> SubscriberDomainService.findSubscriber::{}::{} 캐쉬 삭제", saId, stbMac);
    }

}
