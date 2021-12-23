package com.lguplus.fleta.domain.repository;

import com.lguplus.fleta.data.entity.SampleMember2;
import com.lguplus.fleta.provider.jpa.sample.SampleMember2JpaRepository;
import com.lguplus.fleta.provider.jpa.sample.SampleMemberJpaEmRepository;
import com.lguplus.fleta.repository.SampleMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SampleMemberRepositoryImpl implements SampleMemberRepository {

    private final SampleMember2JpaRepository memberJpaRepository;
    private final SampleMemberJpaEmRepository memberJpaEmRepository;

    @Override
    public Optional<SampleMember2> getMember(int memberId) {
        Optional<SampleMember2> member = memberJpaRepository.findById(memberId);
        Optional<SampleMember2> member2 = memberJpaEmRepository.findMemberById(memberId);
        member.ifPresent(m -> log.debug(">>> member : {}", m));
        member2.ifPresent(m -> log.debug(">>> member2 : {}", m));
        return member;
    }

    @Override
    public List<SampleMember2> getAllMemberList() {
        return memberJpaRepository.findAll();
    }

    @Override
    public void join(SampleMember2 member) {
        log.debug(">>> member before : {}", member);
        SampleMember2 createdMember = memberJpaRepository.save(member);
        log.debug(">>> member after  : {}", member);
        log.debug(">>> createdMember : {}", createdMember);
    }

    @Override
    public void leave(int memberId) {
        memberJpaRepository.deleteById(memberId);
    }
}
