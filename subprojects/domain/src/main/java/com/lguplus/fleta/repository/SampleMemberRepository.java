package com.lguplus.fleta.repository;

import com.lguplus.fleta.data.entity.SampleMember2;

import java.util.List;
import java.util.Optional;

public interface SampleMemberRepository {

    Optional<SampleMember2> getMember(int memberId);

    List<SampleMember2> getAllMemberList();

    void join(SampleMember2 member);

    void leave(int memberId);
}
