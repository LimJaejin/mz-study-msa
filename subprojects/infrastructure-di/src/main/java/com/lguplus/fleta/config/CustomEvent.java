package com.lguplus.fleta.config;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class CustomEvent<T> {

    private final String id;
    private final LocalDateTime issuedDt;
    @Setter
    private String type;
    private final T payload;

    public CustomEvent(String type, T payload) {
        this.id = UUID.randomUUID().toString();
        this.issuedDt = LocalDateTime.now();
        this.type = type;
        this.payload = payload;
    }

    public CustomEvent(T payload) {
        this("", payload);
    }

}
