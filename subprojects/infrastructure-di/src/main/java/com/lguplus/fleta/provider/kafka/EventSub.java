package com.lguplus.fleta.provider.kafka;

import com.lguplus.fleta.config.CustomEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * 이벤트 수신
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class EventSub {

    @Bean
    public Consumer<CustomEvent> sampleUpdated() {
        return (c) -> {
            log.debug(">>> event sub: type: {}, payload: {}", c.getType(), c.getPayload());
            try {
                // 비즈니스 로직 처리
            }
            catch(Exception e) {

            }
        };
    }

    @Bean
    public Consumer<CustomEvent> sampleDeleted() {
        return (c) -> {
            log.debug(">>> event sub: type: {}, payload: {}", c.getType(), c.getPayload());
            try {
                // 비즈니스 로직 처리
            }
            catch(Exception e) {

            }
        };
    }

}
