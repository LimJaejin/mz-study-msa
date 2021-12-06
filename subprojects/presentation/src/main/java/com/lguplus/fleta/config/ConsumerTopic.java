package com.lguplus.fleta.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

@SuppressWarnings("deprecation")
public interface ConsumerTopic {

    String SAMPLE_IN = "sample-in";

    @Input(SAMPLE_IN)
    SubscribableChannel sampleIn();

}
