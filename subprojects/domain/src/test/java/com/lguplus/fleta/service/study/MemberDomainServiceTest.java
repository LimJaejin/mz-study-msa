package com.lguplus.fleta.service.study;

import com.lguplus.fleta.data.dto.study.MemberSearchCond;
import com.lguplus.fleta.data.entity.study.Member;
import com.lguplus.fleta.exception.study.member.DuplicateMemberEmailException;
import com.lguplus.fleta.exception.study.member.InvalidMemberInfoException;
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
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

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
        Member result = memberDomainService.getMember(memberId);
        //then
        assertThat(result).isEqualTo(member);
    }

    @Test
    void getMember_noExists() {
        //given
        given(memberRepository.getMember(any())).willReturn(Optional.empty());
        //when, then
        int memberId = 1;
        assertThatThrownBy(() -> memberDomainService.getMember(memberId))
            .isInstanceOf(NotExistsMemberException.class);
    }

    @Test
    void getMembers() {
        //given
        MemberSearchCond cond = MemberSearchCond.builder().build();
        //when
        List<Member> result = memberDomainService.getMembers(cond);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    void joinMember() {
        //given
        Member member = new Member("Jaejin", "jjlim@mz.co.kr");
        //when
        memberDomainService.joinMember(member);
        
        //then - Mockito 사용
        //verify(memberRepository).joinMember(any());
        //verifyNoMoreInteractions(memberRepository);

        //then - BDDMockito 사용
        then(memberRepository).should().joinMember(any());
        then(memberRepository).should().getMemberByEmail(any());
        then(memberRepository).shouldHaveNoMoreInteractions();
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
        //when, then
        assertThatThrownBy(() -> memberDomainService.joinMember(member))
            .isInstanceOf(InvalidMemberInfoException.class);
    }

    @Test
    void joinMember_duplicateEmail() {
        //given
        Member member = new Member("Jaejin", "jjlim@mz.co.kr");
        given(memberRepository.getMemberByEmail(any())).willReturn(Optional.of(member));
        //when, then
        assertThatThrownBy(() -> memberDomainService.joinMember(member))
            .isInstanceOf(DuplicateMemberEmailException.class);
        //then
        then(memberRepository).should(never()).joinMember(member);
    }

    @Test
    void leaveMember() {
        //given
        //when
        //then
    }
}