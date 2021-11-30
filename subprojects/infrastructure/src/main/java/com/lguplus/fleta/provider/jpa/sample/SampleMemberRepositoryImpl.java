package com.lguplus.fleta.provider.jpa.sample;

import com.lguplus.fleta.data.entity.SampleMember;
import com.lguplus.fleta.repository.SampleMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SampleMemberRepositoryImpl implements SampleMemberRepository {

    private final SampleMemberJpaRepository memberJpaRepository;
    private final SampleMemberJpaEmRepository memberJpaEmRepository;

    @Override
    public Optional<SampleMember> getMember(int memberId) {
        Optional<SampleMember> member = memberJpaRepository.findById(memberId);
        // Optional<SampleMember> member = memberJpaEmRepository.findById(memberId);
        member.ifPresent(m -> log.debug(">>> member : {}", m));
        return member;
    }

    @Override
    public void join(SampleMember member) {
        log.debug(">>> member before : {}", member);
        SampleMember createdMember = memberJpaRepository.save(member);
        log.debug(">>> member after  : {}", member);
        log.debug(">>> createdMember : {}", createdMember);
    }

    @Override
    public void leave(int memberId) {
        memberJpaRepository.deleteById(memberId);
    }
}