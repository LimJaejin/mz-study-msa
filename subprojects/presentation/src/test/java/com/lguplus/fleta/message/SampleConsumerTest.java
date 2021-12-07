package com.lguplus.fleta.message;

import com.lguplus.fleta.BootConfig;
import com.lguplus.fleta.config.ConsumerChannel;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import com.lguplus.fleta.data.message.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = { BootConfig.class })
@DisplayName("샘플 메시지 컨슈머 테스트")
class SampleConsumerTest {

    @Autowired
    private ConsumerChannel consumerChannel;

    private SampleMemberDto dto;

    private Message<CustomMessage<SampleMemberDto>> buildMessage(String headerValue, SampleMemberDto dto) {
        return MessageBuilder.withPayload(new CustomMessage<>(dto))
                .setHeader(Producer.HEADER_NAME, headerValue)
                .build();
    }
    
    @BeforeEach
    void setup() {
        this.dto = new SampleMemberDto("전강욱", "realsnake1975@gmail.com");
    }

    @Test
    @DisplayName("#1. 샘플 등록 메시지 수신 테스트")
    void testSampleInserted() {
        boolean runResult = true;

        try {
            Message<CustomMessage<SampleMemberDto>> testMessage = this.buildMessage("sample-inserted", this.dto);
            this.consumerChannel.sampleIn().send(testMessage);
        }
        catch (Exception e) {
            log.error(">>> 샘플 등록 메시지 수신 테스트 오류", e);
            runResult = false;
        }

        assertTrue(runResult);
    }

}
