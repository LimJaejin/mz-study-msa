package com.lguplus.fleta.service.sample;

import com.lguplus.fleta.data.dto.sample.SampleCustomMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SampleMemberService {

    private final SampleMemberDomainService memberDomainService;

    @Transactional
    public void initServiceData() {
        memberDomainService.initData();
    }

    public SampleCustomMemberDto getMember(int memberId) {
        return memberDomainService.getMember(memberId);
    }
}
