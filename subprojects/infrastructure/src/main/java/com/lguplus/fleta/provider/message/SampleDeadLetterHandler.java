package com.lguplus.fleta.provider.message;

import com.lguplus.fleta.config.ConsumerChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.nio.charset.StandardCharsets;

/**
 * dead letter(메시지 구독 오류) 핸들링
 */
@SuppressWarnings("deprecation")
@Slf4j
@RequiredArgsConstructor
@EnableBinding(ConsumerChannel.class)
public class SampleDeadLetterHandler {

//    /**
//     * dead letter 로깅 & 핸들링
//     *
//     * @param deadLetter
//     */
//    @StreamListener(value = ConsumerChannel.DLQ_SAMPLE_IN)
//    public void handleDlq(Message<?> deadLetter) {
//        log.error(">>> sample dlq, header: {}\npayload: {}\nheader: {}", deadLetter.getHeaders(), deadLetter.getPayload());
//
////        deadLetter.getHeaders().forEach((key, value) -> log.info(">>> key: {}, value: {}", key, value.toString()));
//    }

    /**
     * dead letter 로깅 & 핸들링
     *
     * @param topic
     * @param consumerGroup
     * @param error
     * @param payload
     */
    @StreamListener(value = ConsumerChannel.DLQ_SAMPLE_IN)
    public void handleSampleDeadLetter(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic
            , @Header(KafkaHeaders.GROUP_ID) String consumerGroup
            , @Header("x-exception-message") byte[] error
            , @Header("x-message-type") String messageType
            , byte[] payload
    ) {
        // 모든 로그는 EFK에 저장되므로, 아래 로그 또한 EFK에 저장된다.
        log.error(">>> sample dlq\ntopic: {}\nconsumerGroup: {}\nmessageType: {}\nerror: {}\npayload: {}"
                , topic
                , consumerGroup
                , messageType
                , new String(error, StandardCharsets.UTF_8)
                , new String(payload, StandardCharsets.UTF_8)
        );
    }

}
