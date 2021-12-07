package com.lguplus.fleta.provider.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lguplus.fleta.BootConfig;
import com.lguplus.fleta.config.ProducerChannel;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import com.lguplus.fleta.data.message.CustomMessage;
import com.lguplus.fleta.message.Producer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(classes = { BootConfig.class })
@DisplayName("샘플 메시지 프로듀서 테스트")
class SampleProducerImplTest {

    @Autowired
    private SampleProducerImpl sampleProducer;

    @Autowired
    private ProducerChannel producerChannel;

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    private ObjectMapper objectMapper;

    private SampleMemberDto dto;

    @BeforeEach
    void setup() {
        this.dto = new SampleMemberDto("전강욱", "realsnake1975@gmail.com");
    }

    @SuppressWarnings("rawtypes")
    @Test
    @DisplayName("#1. 샘플 등록 메시지 발송 테스트")
    void testOnInserted() throws JsonProcessingException {
        this.sampleProducer.sendMessage("sample-inserted", this.dto);

        final Message<?> message = this.messageCollector.forChannel(this.producerChannel.sampleOut()).poll();

        assert message != null;
        String headerName = (String) message.getHeaders().get(Producer.HEADER_NAME);
        log.debug(">>> message header: {}", headerName);

        Object o = message.getPayload();
        log.debug(">>> message payload: {}", o);

        CustomMessage customMessage = this.objectMapper.readValue(o.toString(), new TypeReference<CustomMessage<SampleMemberDto>>() {});
        log.debug(">>> sampleMemberDto: {}", customMessage.getPayload());

        SampleMemberDto smd = (SampleMemberDto) customMessage.getPayload();

        assertAll(
            () -> assertEquals(dto.getName(), smd.getName()),
            () -> assertEquals(dto.getEmail(), smd.getEmail())
        );
    }

}
