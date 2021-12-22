package com.lguplus.fleta.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SampleMessageStreamService {

    private final MessageStreamSender messageStreamSender;

    public void sendMessage(String message) {
        messageStreamSender.send(message);
    }
}