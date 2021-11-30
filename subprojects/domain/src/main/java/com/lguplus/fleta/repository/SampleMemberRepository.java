package com.lguplus.fleta.repository;

import com.lguplus.fleta.data.entity.SampleMember;

import java.util.Optional;

public interface SampleMemberRepository {

    Optional<SampleMember> getMember(int memberId);

    void join(SampleMember member);

    void leave(int memberId);
}
