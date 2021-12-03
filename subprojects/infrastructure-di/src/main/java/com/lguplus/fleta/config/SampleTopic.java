package com.lguplus.fleta.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

@SuppressWarnings("deprecation")
public interface SampleTopic {

    String INPUT = "sample-consumer";

    String OUTPUT = "sample-producer";

    @Input(INPUT)
    SubscribableChannel input();

    @Output(OUTPUT)
    MessageChannel output();

}
