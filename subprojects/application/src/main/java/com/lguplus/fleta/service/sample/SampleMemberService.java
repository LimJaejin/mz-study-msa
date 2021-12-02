package com.lguplus.fleta.service.sample;

import com.lguplus.fleta.data.dto.sample.SampleCustomMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SampleMemberService {

    private final SampleMemberServiceStep memberServiceStep;
    private final SampleMemberDomainService memberDomainService;

    @Transactional
    public void initServiceData() {
        memberDomainService.initData();
    }

    public SampleCustomMemberDto getMember(int memberId) {
        SampleCustomMemberDto member = memberDomainService.getMember(memberId);
        log.debug(">>> member : {}", member);
        return member;
    }

    public List<SampleCustomMemberDto> getAllMemberList() {
        return memberDomainService.getAllMemberList();
    }

    public String doMainLogic() {
        // Step 1
        String subLogic01 = memberDomainService.doSubLogic01();
        // Step 2
        String subLogic02 = memberDomainService.doSubLogic02();
        // Step 3, 4
        String subLogic03And04 = memberServiceStep.doSubLogic03And04();
        return subLogic01 + subLogic02 + subLogic03And04;
    }
}
