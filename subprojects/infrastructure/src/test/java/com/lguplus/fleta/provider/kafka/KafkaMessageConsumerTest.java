package com.lguplus.fleta.provider.kafka;

import com.lguplus.fleta.listener.MessageStreamListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KafkaMessageConsumerTest {

    private final String MESSAGE = "TEST_MESSAGE";

    @Mock
    MessageStreamListener messageStreamListener;

    @InjectMocks
    KafkaMessageConsumer kafkaMessageConsumer;

    @Test
    void listen() {
        kafkaMessageConsumer.listen(MESSAGE);
    }
}