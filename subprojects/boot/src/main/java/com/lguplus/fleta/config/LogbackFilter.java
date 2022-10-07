package com.lguplus.fleta.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogbackFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        log.debug(">>> event.getMarker() : {}", event.getMarker());
        if (event.getMarker() == Slf4jMarkerConst.FULL_TEXT_MARKER) {
            return FilterReply.DENY;
        }
        return FilterReply.ACCEPT;
    }
}
