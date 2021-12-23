package com.lguplus.fleta.client;

import com.lguplus.fleta.data.dto.common.subscriber.SubscriberInfoDto;
import com.lguplus.fleta.data.dto.response.InnerResponseDto;

public interface SubscriberInfoClient {

    InnerResponseDto<SubscriberInfoDto> findBySaIdAndStbMac(String saId, String stbMac);

}
