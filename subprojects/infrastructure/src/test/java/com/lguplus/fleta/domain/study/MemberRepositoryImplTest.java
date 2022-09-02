package com.lguplus.fleta.domain.study;

import com.lguplus.fleta.data.dto.study.MemberSearchCond;
import com.lguplus.fleta.data.entity.study.Member;
import com.lguplus.fleta.provider.jpa.study.MemberJpaEmRepository;
import com.lguplus.fleta.provider.jpa.study.MemberJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @ParameterizedTest
    @CsvSource(value = {
        "Jaejin:jjlim@mz.co.kr",
        "Jaejin:",
        ":jjlim@mz.co.kr",
        ":"
    }, delimiter = ':')
    void getMembersByCond(String name, String email) {
        MemberSearchCond cond = MemberSearchCond.builder()
            .name(name)
            .email(email)
            .build();
        memberRepository.getMembersByCond(cond);
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