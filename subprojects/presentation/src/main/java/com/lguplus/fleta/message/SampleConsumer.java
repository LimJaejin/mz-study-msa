package com.lguplus.fleta.message;

import com.lguplus.fleta.data.message.CustomMessage;
import com.lguplus.fleta.config.ConsumerChannel;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * 이벤트 수신
 */
@SuppressWarnings("deprecation")
@Slf4j
@RequiredArgsConstructor
@EnableBinding(ConsumerChannel.class)
public class SampleConsumer {

    @StreamListener(value = ConsumerChannel.SAMPLE_IN, condition = "headers['x-message-type']=='sample-inserted'")
    public void sampleInserted(@Payload CustomMessage<SampleMemberDto> message) {
        log.info(">>> message sub, message: {}", message.toString());
        // application layer의 usecase 호출
    }

}
