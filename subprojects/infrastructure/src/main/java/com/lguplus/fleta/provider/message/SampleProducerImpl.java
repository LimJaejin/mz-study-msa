package com.lguplus.fleta.provider.message;

import com.lguplus.fleta.data.message.CustomMessage;
import com.lguplus.fleta.config.ProducerChannel;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import com.lguplus.fleta.message.Producer;
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
@EnableBinding(ProducerChannel.class)
public class SampleProducerImpl implements Producer<SampleMemberDto> {

    private final ProducerChannel producerChannel;

    private Message<CustomMessage<SampleMemberDto>> buildMessage(String headerValue, SampleMemberDto dto) {
        return MessageBuilder.withPayload(new CustomMessage<>(dto))
                .setHeader(HEADER_NAME, headerValue)
                .build();
    }

    @Override
    public void sendMessage(String headerValue, SampleMemberDto dto) {
        log.debug(">>> message pub, header: {}, payload: {}", headerValue, dto.toString());

        this.producerChannel.sampleOut().send(this.buildMessage(headerValue, dto));
    }

}
