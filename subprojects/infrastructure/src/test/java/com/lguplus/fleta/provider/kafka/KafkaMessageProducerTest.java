package com.lguplus.fleta.provider.kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaMessageProducerTest {

    private final String KEY = "TEST_KEY";
    private final String MESSAGE = "TEST_MESSAGE";

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    KafkaMessageProducer kafkaMessageProducer;

    @BeforeEach
    void beforeEach() {
        when(kafkaTemplate.send(anyString(), anyString(), anyString())).thenReturn(null);
    }

    @Test
    void send() {
        kafkaMessageProducer.send(MESSAGE);
    }

    @Test
    void sendWithKey() {
        kafkaMessageProducer.send(MESSAGE, KEY);
    }
}