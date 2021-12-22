package com.lguplus.fleta.domain.message;

import com.lguplus.fleta.message.MessageStreamSender;
import com.lguplus.fleta.provider.message.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageStreamSenderImpl implements MessageStreamSender {

    private final KafkaMessageProducer kafkaMessageProducer;

    @Override
    public void send(String message) {
        kafkaMessageProducer.send(message);
    }

    @Override
    public void send(String message, String key) {
        kafkaMessageProducer.send(message, key);
    }
}