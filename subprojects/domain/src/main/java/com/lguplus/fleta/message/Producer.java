package com.lguplus.fleta.message;

public interface Producer<T> {

    String HEADER_NAME = "x-message-type";

    void sendMessage(String headerValue, T dto);

}
