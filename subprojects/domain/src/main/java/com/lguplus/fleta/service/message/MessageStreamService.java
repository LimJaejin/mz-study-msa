package com.lguplus.fleta.service.message;

import com.lguplus.fleta.message.MessageStreamSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageStreamService {

    private final MessageStreamSender messageStreamSender;

    public void sendMessage(String message) {
        messageStreamSender.send(message);
    }
}
