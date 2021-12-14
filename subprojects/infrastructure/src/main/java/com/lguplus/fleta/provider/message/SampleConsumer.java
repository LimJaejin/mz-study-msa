package com.lguplus.fleta.provider.message;

import com.lguplus.fleta.config.ConsumerChannel;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import com.lguplus.fleta.data.message.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

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

    /**
     * dead letter 로깅
     *
     * @param deadLetter
     */
    @StreamListener(value = ConsumerChannel.DLQ_SAMPLE_IN)
    public void handleDlq(Message<?> deadLetter) {
        log.error(">>> sample dlq, header: {}\npayload: {}", deadLetter.getHeaders(), deadLetter.getPayload());
    }

}
