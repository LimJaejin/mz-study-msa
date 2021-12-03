package com.lguplus.fleta.provider.kafka;

import com.lguplus.fleta.config.CustomEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

/**
 * 이벤트 발행
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class EventPub {

    private final StreamBridge streamBridge;

    public void onUpdated(CustomEvent<?> event)  {
        log.debug(">>> event pub, type: {}", event.getType());
        this.streamBridge.send(BindingName.sampleUpdated.name(), event);
    }

    public void onDeleted(CustomEvent<?> event)  {
        log.debug(">>> event pub, type: {}", event.getType());
        this.streamBridge.send(BindingName.sampleDeleted.name(), event);
    }

    enum BindingName {
        sampleUpdated,
        sampleDeleted
        ;
    }

}
