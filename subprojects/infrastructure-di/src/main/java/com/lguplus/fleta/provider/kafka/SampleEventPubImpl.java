package com.lguplus.fleta.provider.kafka;

import com.lguplus.fleta.config.CustomMessage;
import com.lguplus.fleta.config.SampleTopic;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import com.lguplus.fleta.service.sample.message.SampleEventPub;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * 이벤트 발행
 */
@SuppressWarnings("deprecation")
@Slf4j
@RequiredArgsConstructor
@EnableBinding(SampleTopic.class)
public class SampleEventPubImpl implements SampleEventPub {

    private final SampleTopic sampleTopic;

    @Override
    public void onInserted(SampleMemberDto dto) {
        log.debug(">>> event pub, payload: {}", dto.toString());

        Message<CustomMessage<SampleMemberDto>> message = MessageBuilder.withPayload(new CustomMessage<>(dto))
                .setHeader("event-type", "inserted")
                .build();
        this.sampleTopic.output().send(message);
    }

    @Override
    public void onUpdated(SampleMemberDto dto) {

    }

    @Override
    public void onDeleted(SampleMemberDto dto) {

    }

}
