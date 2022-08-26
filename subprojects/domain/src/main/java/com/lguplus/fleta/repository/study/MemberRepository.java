package com.lguplus.fleta.repository.study;

import com.lguplus.fleta.data.dto.study.MemberSearchCond;
import com.lguplus.fleta.data.entity.study.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> getMember(Integer id);

    List<Member> getMembers();

    List<Member> getMembersByEmail(String email);

    List<Member> getMembersByCond(MemberSearchCond cond);

    void joinMember(Member member);

    void leaveMember(Integer id);
}
