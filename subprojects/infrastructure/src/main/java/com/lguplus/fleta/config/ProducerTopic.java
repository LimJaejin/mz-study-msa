package com.lguplus.fleta.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

@SuppressWarnings("deprecation")
public interface ProducerTopic {

    String SAMPLE_OUT = "sample-out";

    @Output(SAMPLE_OUT)
    MessageChannel sampleOut();

}
