package com.lguplus.fleta.provider.message;

import com.lguplus.fleta.BootConfig;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import com.lguplus.fleta.data.message.Payload;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = { BootConfig.class })
@DisplayName("샘플 메시지 컨슈머 테스트")
class SampleConsumerTest {

//    @Autowired
//    private ConsumerChannel consumerChannel;

    @Autowired
    private SampleConsumer sampleConsumer;

    private Payload<SampleMemberDto> payload;

//    private Message<Payload<SampleMemberDto>> buildMessage(String headerValue, SampleMemberDto dto) {
//        return MessageBuilder.withPayload(new Payload<>(dto))
//                .setHeader(Producer.HEADER_NAME, headerValue)
//                .build();
//    }
    
    @BeforeEach
    void setup() {
        SampleMemberDto dto = new SampleMemberDto("전강욱", "realsnake1975@gmail.com");
        this.payload = new Payload<>(dto);
    }

    @Test
    @DisplayName("#1. 샘플 등록 메시지 수신 테스트")
    void testReceiveSampleInserted() {
        boolean runResult = true;

        try {
//            Message<Payload<SampleMemberDto>> testMessage = this.buildMessage("sample-inserted", this.dto);
//            this.consumerChannel.sampleIn().send(testMessage);
            this.sampleConsumer.receiveSampleInserted(this.payload);
        }
        catch (Exception e) {
            log.error(">>> 샘플 등록 메시지 수신 테스트 오류", e);
            runResult = false;
        }

        assertTrue(runResult);
    }

}
