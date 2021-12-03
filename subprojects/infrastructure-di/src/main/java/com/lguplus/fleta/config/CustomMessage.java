package com.lguplus.fleta.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString
public class CustomMessage<T> implements Serializable {

    private String id;
    private LocalDateTime issuedDt;
    private transient T payload;

    public CustomMessage(T payload) {
        this.id = UUID.randomUUID().toString();
        this.issuedDt = LocalDateTime.now();
        this.payload = payload;
    }

}
