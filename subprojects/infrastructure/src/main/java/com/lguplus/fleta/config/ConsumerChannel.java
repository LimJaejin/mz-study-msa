package com.lguplus.fleta.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

@SuppressWarnings("deprecation")
public interface ConsumerChannel {

    String SAMPLE_IN = "sample-in";
    String DLQ_SAMPLE_IN = "dlq-sample-in";

    @Input(SAMPLE_IN)
    SubscribableChannel sampleIn();

    @Input(DLQ_SAMPLE_IN)
    SubscribableChannel dlqSampleIn();

}
