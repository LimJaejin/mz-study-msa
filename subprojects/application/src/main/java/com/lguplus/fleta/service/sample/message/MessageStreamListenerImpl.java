package com.lguplus.fleta.service.sample.message;

import com.lguplus.fleta.service.MessageStreamListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageStreamListenerImpl implements MessageStreamListener {

    @Override
    public void procMessageStream(String message) {
        log.debug(">>> message : {}", message);
    }
}
