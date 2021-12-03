package com.lguplus.fleta.service.sample.message;

import com.lguplus.fleta.data.dto.sample.SampleMemberDto;

public interface SampleEventPub {

    void onInserted(SampleMemberDto dto);

    void onUpdated(SampleMemberDto dto);

    void onDeleted(SampleMemberDto dto);

}
