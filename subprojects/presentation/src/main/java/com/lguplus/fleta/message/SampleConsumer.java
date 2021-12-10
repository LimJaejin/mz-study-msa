package com.lguplus.fleta.message;

import com.lguplus.fleta.data.message.Payload;
import com.lguplus.fleta.config.ConsumerChannel;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * 이벤트 수신
 */
@SuppressWarnings("deprecation")
@Slf4j
@RequiredArgsConstructor
@EnableBinding(ConsumerChannel.class)
public class SampleConsumer {

    @StreamListener(value = ConsumerChannel.SAMPLE_IN, condition = "headers['x-message-type']=='sample-inserted'")
    public void sampleInserted(@org.springframework.messaging.handler.annotation.Payload Payload<SampleMemberDto> message) {
        log.info(">>> message sub, message: {}", message.toString());
        // application layer의 usecase 호출
    }

}
