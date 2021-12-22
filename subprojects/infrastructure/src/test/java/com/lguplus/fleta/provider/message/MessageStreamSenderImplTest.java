package com.lguplus.fleta.provider.message;

import com.lguplus.fleta.domain.message.MessageStreamSenderImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class MessageStreamSenderImplTest {

    private final String KEY = "TEST_KEY";
    private final String MESSAGE = "TEST_MESSAGE";

    @Mock
    private KafkaMessageProducer kafkaMessageProducer;

    @InjectMocks
    private MessageStreamSenderImpl messageStreamSenderImpl;

    @Test
    void testSendString() {
        doNothing().when(kafkaMessageProducer).send(anyString());
        messageStreamSenderImpl.send(MESSAGE);
    }

    @Test
    void testSendStringString() {
        doNothing().when(kafkaMessageProducer).send(anyString(), anyString());
        messageStreamSenderImpl.send(MESSAGE, KEY);
    }

}
