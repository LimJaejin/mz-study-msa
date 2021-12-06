package com.lguplus.fleta.provider.message;

import com.lguplus.fleta.data.message.CustomMessage;
import com.lguplus.fleta.config.ProducerTopic;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import com.lguplus.fleta.message.SampleProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * 이벤트 발행
 */
@SuppressWarnings("deprecation")
@Slf4j
@RequiredArgsConstructor
@EnableBinding(ProducerTopic.class)
public class SampleProducerImpl implements SampleProducer {

    private final ProducerTopic producerTopic;

    @Override
    @SendTo(ProducerTopic.SAMPLE_OUT)
    public void onInserted(SampleMemberDto dto) {
        log.debug(">>> event pub, payload: {}", dto.toString());

        Message<CustomMessage<SampleMemberDto>> message = MessageBuilder.withPayload(new CustomMessage<>(dto))
                .setHeader("x-event-type", "sample-inserted")
                .build();
        this.producerTopic.sampleOut().send(message);
    }

    @Override
    public void onUpdated(SampleMemberDto dto) {

    }

    @Override
    public void onDeleted(SampleMemberDto dto) {

    }

}
