package com.lguplus.fleta.data.message;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
// dead letter를 발생시키기 위해 주석 처리
//@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class Payload<T> implements Serializable {

    // 메시지 고유 아이디
    private String messageId;
    // 메시지 유형
    private String messageType;
    // 메시지 생성 일시
    private LocalDateTime messageCreatedDt;
    // 메시지 본문
    private T messageBody;

    public Payload(T messageBody) {
        this.messageId = UUID.randomUUID().toString();
        this.messageCreatedDt = LocalDateTime.now();
        this.messageBody = messageBody;
    }

    public Payload(String messageType, T messageBody) {
        this(messageBody);
        this.messageType = messageType;
    }

}
