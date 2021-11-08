package com.lguplus.fleta.service.message;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageStreamListenerImplTest {

    private final String MESSAGE = "TEST_MESSAGE";

    @InjectMocks
    private MessageStreamListenerImpl messageStreamListenerImpl;

    @Test
    void procMessageStream() {
        messageStreamListenerImpl.procMessageStream(MESSAGE);
    }
}
