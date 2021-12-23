package com.lguplus.fleta.service;

import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import com.lguplus.fleta.dto.InnerEvent;
import com.lguplus.fleta.message.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class SampleServiceListener {

    private final Producer<SampleMemberDto> sampleProducer;

    @TransactionalEventListener(condition = "#event.type == 'sample-inserted'", phase = TransactionPhase.AFTER_COMMIT)
    public void listenSampleInserted(InnerEvent<?> event) {
        log.debug(">>> 트랜잭션 완료 이후, 메시지 발행. event: {}", event);
        this.sampleProducer.sendMessage(event.getType(), (SampleMemberDto) event.getDto());
    }

}
