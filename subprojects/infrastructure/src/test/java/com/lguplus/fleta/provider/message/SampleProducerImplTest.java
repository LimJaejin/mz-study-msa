package com.lguplus.fleta.provider.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lguplus.fleta.BootConfig;
import com.lguplus.fleta.config.ProducerTopic;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import com.lguplus.fleta.data.message.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(classes = { BootConfig.class })
@DisplayName("샘플 메시지 프로듀서 테스트")
class SampleProducerImplTest {

    @Autowired
    private SampleProducerImpl sampleProducer;

    @Autowired
    private ProducerTopic producerTopic;

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    private ObjectMapper objectMapper;

    SampleMemberDto dto = null;

    @BeforeEach
    void setup() {
        this.dto = new SampleMemberDto("전강욱", "realsnake1975@gmail.com");
    }

    @SuppressWarnings("rawtypes")
    @Test
    @DisplayName("#1. 샘플 등록 메시지 테스트")
    void testOnInserted() throws JsonProcessingException {
        this.sampleProducer.onInserted(this.dto);

        Object o = Objects.requireNonNull(this.messageCollector.forChannel(this.producerTopic.sampleOut()).poll()).getPayload();
        log.debug(">>> {}", o);

        CustomMessage customMessage = this.objectMapper.readValue(o.toString(), new TypeReference<CustomMessage<SampleMemberDto>>() {});
        log.debug(">>> sampleMemberDto: {}", customMessage.getPayload());

        SampleMemberDto smd = (SampleMemberDto) customMessage.getPayload();

        assertAll(
            () -> {
                assertEquals(dto.getName(), smd.getName());
            },
            () -> {
                assertEquals(dto.getEmail(), smd.getEmail());
            }
        );
    }

}
