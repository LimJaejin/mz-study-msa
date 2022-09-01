package com.lguplus.fleta.domain.study;

import com.lguplus.fleta.data.dto.study.MemberSearchCond;
import com.lguplus.fleta.data.entity.study.Member;
import com.lguplus.fleta.provider.jpa.study.MemberJpaEmRepository;
import com.lguplus.fleta.provider.jpa.study.MemberJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

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
    void existsMember() {
        //given
        BDDMockito.given(memberJpaRepository.existsById(any())).willReturn(true);
        int memberId = 1;
        //when
        boolean exists = memberRepository.existsMember(memberId);
        //then
        assertThat(exists).isTrue();
    }

    @Test
    void getMembers() {
        memberRepository.getMembers();
    }

    @Test
    void getMembersByEmail() {
        String email = "jjlim@mz.co.kr";
        memberRepository.getMemberByEmail(email);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "Jaejin:jjlim01@mz.co.kr",
        "Jaejin:",
        ":jjlim02@mz.co.kr",
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