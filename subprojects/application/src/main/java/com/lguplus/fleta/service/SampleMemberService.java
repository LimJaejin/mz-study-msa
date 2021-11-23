package com.lguplus.fleta.service;

import com.lguplus.fleta.data.dto.sample.SampleMemberDomainDto;

public interface SampleMemberService {

    void searchMemberByNameAndEmail(SampleMemberDomainDto memberDto);
}
