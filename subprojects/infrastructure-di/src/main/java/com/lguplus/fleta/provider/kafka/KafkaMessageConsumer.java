package com.lguplus.fleta.provider.kafka;

import com.lguplus.fleta.service.MessageStreamListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Deprecated
@Slf4j
@RequiredArgsConstructor
//@Component
public class KafkaMessageConsumer {

    private static final String TOPIC = "MSA_BOILERPLATE";     // TODO : Topic 이름 지정
    private static final String GROUP_ID = "MSA_BOILERPLATE_GROUP"; // TODO : ConsumerGroupID 이름 지정

    private final MessageStreamListener procMessageStream;

//    @KafkaListener(topics = KafkaMessageConsumer.TOPIC, groupId = KafkaMessageConsumer.GROUP_ID)
    public void listen(String message) {
        log.debug(">>> message : {}", message);
        procMessageStream.procMessageStream(message);
    }
}
