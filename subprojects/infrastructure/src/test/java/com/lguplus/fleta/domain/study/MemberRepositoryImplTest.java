package com.lguplus.fleta.domain.study;

import com.lguplus.fleta.data.dto.study.MemberSearchCond;
import com.lguplus.fleta.data.entity.study.Member;
import com.lguplus.fleta.provider.jpa.study.MemberJpaEmRepository;
import com.lguplus.fleta.provider.jpa.study.MemberJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberRepositoryImplTest {

    @InjectMocks MemberRepositoryImpl memberRepository;
    @Mock MemberJpaRepository memberJpaRepository;
    @Mock MemberJpaEmRepository memberJpaEmRepository;

    @Test
    void getMember() {
        int memberId = 1;
        memberRepository.getMember(memberId);
    }

    @Test
    void getMembers() {
        memberRepository.getMembers();
    }

    @Test
    void getMembersByEmail() {
        String email = "jjlim@mz.co.kr";
        memberRepository.getMembersByEmail(email);
    }

    @Test
    void getMembersByCond() {
        memberRepository.getMembersByCond(MemberSearchCond.builder().build());
    }

    @Test
    void joinMember() {
        Member member = new Member("Jaejin", "jjlim@mz.co.kr");
        memberRepository.joinMember(member);
    }

    @Test
    void leaveMember() {
        int memberId = 1;
        memberRepository.leaveMember(memberId);
    }
}