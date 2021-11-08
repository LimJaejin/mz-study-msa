package com.lguplus.fleta.message;

public interface MessageStreamSender {

    void send(String message);

    void send(String message, String key);
}
