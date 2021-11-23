package com.lguplus.fleta.service.sample;

import com.lguplus.fleta.data.dto.sample.SampleMemberDomainDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SampleMemberServiceImpl implements SampleMemberService {

    @Override
    public void searchMemberByNameAndEmail(SampleMemberDomainDto memberDto) {
        log.debug(">>> memberDto : {}", memberDto);
    }
}
