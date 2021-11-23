package com.lguplus.fleta.service;

import com.lguplus.fleta.data.dto.sample.SampleMemberDomainDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SampleMemberServiceImpl implements SampleMemberService {

    @Override
    public void searchMemberByNameAndEmail(SampleMemberDomainDto memberDto) {
        log.debug(">>> memberDto : {}", memberDto);
    }
}
