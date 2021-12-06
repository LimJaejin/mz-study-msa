package com.lguplus.fleta.message;

import com.lguplus.fleta.data.dto.sample.SampleMemberDto;

public interface SampleProducer {

    void onInserted(SampleMemberDto dto);

    void onUpdated(SampleMemberDto dto);

    void onDeleted(SampleMemberDto dto);

}
