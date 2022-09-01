package com.lguplus.fleta.service.study;

import com.lguplus.fleta.data.dto.study.MemberSearchCond;
import com.lguplus.fleta.data.entity.study.Member;
import com.lguplus.fleta.exception.study.member.DuplicateMemberEmailException;
import com.lguplus.fleta.exception.study.member.NoValidatedMemberInfoException;
import com.lguplus.fleta.exception.study.member.NotExistsMemberException;
import com.lguplus.fleta.repository.study.MemberRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberDomainServiceTest {

    @InjectMocks MemberDomainService memberDomainService;
    @Mock MemberRepository memberRepository;

    @Test
    void getMember() {
        //given
        Member member = new Member("Jaejin", "jjlim@mz.co.kr");
        given(memberRepository.getMember(any())).willReturn(Optional.of(member));
        //when
        int memberId = 1;
        Member resultMember = memberDomainService.getMember(memberId);
        //then
        assertThat(resultMember).isEqualTo(member);
    }

    @Test
    void getMember_noExists() {
        //given
        given(memberRepository.getMember(any())).willReturn(Optional.empty());
        int invalidMemberId = 999;
        //when & then
        assertThatThrownBy(() -> memberDomainService.getMember(invalidMemberId))
            .isInstanceOf(NotExistsMemberException.class);
    }

    @Test
    void getMembers() {
        //given
        MemberSearchCond cond = MemberSearchCond.builder().build();
        given(memberRepository.getMembersByCond(cond)).willReturn(List.of());
        //when
        List<Member> resultMembers = memberDomainService.getMembers(cond);
        //then
        assertThat(resultMembers).isEmpty();
    }

    @Test
    void joinMember() {
        //given
        Member member = new Member("Jaejin", "jjlim@mz.co.kr");
        //when
        memberDomainService.joinMember(member);
        //then
        verify(memberRepository, times(1)).joinMember(member);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "Jaejin:",
        ":jjlim@mz.co.kr",
        ":"
    }, delimiter = ':')
    void joinMember_noValidate(String name, String email) {
        //given
        Member member = new Member(name, email);
        //when & then
        assertThatThrownBy(() -> memberDomainService.joinMember(member))
            .isInstanceOf(NoValidatedMemberInfoException.class);
        //then
        verify(memberRepository, never()).joinMember(member);
    }

    @Test
    void joinMember_duplicateEmail() {
        //given
        Member member = new Member("Jaejin", "jjlim@mz.co.kr");
        given(memberRepository.getMemberByEmail(any())).willReturn(Optional.of(member));
        //when & then
        assertThatThrownBy(() -> memberDomainService.joinMember(member))
            .isInstanceOf(DuplicateMemberEmailException.class);
        //then
        verify(memberRepository, never()).joinMember(member);
    }

    @Test
    void leaveMember() {
        //given
        int memberId = 1;
        given(memberRepository.existsMember(memberId)).willReturn(true);
        //when
        memberDomainService.leaveMember(memberId);
        //then
        verify(memberRepository).leaveMember(memberId);
    }

    @Test
    void leaveMember_noExists() {
        //given
        int memberId = 1;
        given(memberRepository.existsMember(memberId)).willReturn(false);
        //when&then
        assertThatThrownBy(() -> memberDomainService.leaveMember(memberId))
            .isInstanceOf(NotExistsMemberException.class);
        //then
        verify(memberRepository, never()).leaveMember(memberId);
    }
}
