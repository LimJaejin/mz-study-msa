package com.lguplus.fleta.provider.kafka;

import com.lguplus.fleta.config.CustomMessage;
import com.lguplus.fleta.config.SampleTopic;
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
@EnableBinding(SampleTopic.class)
public class SampleEventSub {

    @StreamListener(value = SampleTopic.INPUT, condition = "headers['event-type']=='inserted'")
    public void subSampleInserted(@Payload CustomMessage<SampleMemberDto> message) {
        log.info(">>> event sub, message: {}", message.toString());
    }

}
