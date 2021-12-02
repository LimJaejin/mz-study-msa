package com.lguplus.fleta.repository;

import com.lguplus.fleta.data.entity.SampleMember;

import java.util.List;
import java.util.Optional;

public interface SampleMemberRepository {

    Optional<SampleMember> getMember(int memberId);

    List<SampleMember> getAllMemberList();

    void join(SampleMember member);

    void leave(int memberId);
}
