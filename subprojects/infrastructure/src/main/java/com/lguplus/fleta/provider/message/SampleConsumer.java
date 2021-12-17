package com.lguplus.fleta.provider.message;

import com.lguplus.fleta.config.ConsumerChannel;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import com.lguplus.fleta.data.message.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * 메시지 구독
 */
@SuppressWarnings("deprecation")
@Slf4j
@RequiredArgsConstructor
@EnableBinding(ConsumerChannel.class)
public class SampleConsumer {

    // presentation 레이어의 클래스 주입

    /**
     * 메시지 타입이 'sample-inserted'인 메시지 구독
     *
     * @param message payload
     */
    @StreamListener(value = ConsumerChannel.SAMPLE_IN, condition = "headers['x-message-type']=='sample-inserted'")
    public void sampleInserted(@org.springframework.messaging.handler.annotation.Payload Payload<SampleMemberDto> message) {
        log.info(">>> message sub, message: {}", message.toString());
        // 위에서 주입한 presentation 레이어 클래스의 메소드 호출
    }

}
