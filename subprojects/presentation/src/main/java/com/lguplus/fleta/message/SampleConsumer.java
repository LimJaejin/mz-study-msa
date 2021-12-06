package com.lguplus.fleta.message;

import com.lguplus.fleta.data.message.CustomMessage;
import com.lguplus.fleta.config.ConsumerTopic;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * 이벤트 수신
 */
@SuppressWarnings("deprecation")
@Slf4j
@RequiredArgsConstructor
@EnableBinding(ConsumerTopic.class)
public class SampleConsumer {

    @StreamListener(value = ConsumerTopic.SAMPLE_IN, condition = "headers['x-event-type']=='sample-inserted'")
    public void sampleInserted(@Payload CustomMessage<SampleMemberDto> message) {
        log.info(">>> event sub, message: {}", message.toString());
        // application layer의 usecase 호출
    }

}
