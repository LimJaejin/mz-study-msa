package com.lguplus.fleta.domain.study;

import com.lguplus.fleta.config.InfrastructureConfig;
import com.lguplus.fleta.data.dto.study.MemberSearchCond;
import com.lguplus.fleta.data.entity.study.Member;
import com.lguplus.fleta.provider.jpa.study.MemberJpaRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

//@Disabled
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ComponentScan(basePackageClasses = InfrastructureConfig.class)
class MemberRepositoryImplDataJpaTest {

    @Autowired MemberRepositoryImpl memberRepository;
    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    void getMember() {
        //given
        Member member = new Member("Jaejin", "jjlim@mz.co.kr");
        memberJpaRepository.save(member);
        //when
        Optional<Member> resultMember = memberRepository.getMember(member.getId());
        //then
        assertThat(resultMember).isPresent().contains(member);
    }

    @Test
    void existsMember() {
        //given
        Member member = new Member("Jaejin", "jjlim@mz.co.kr");
        memberJpaRepository.save(member);
        //when
        boolean exists = memberRepository.existsMember(member.getId());
        //then
        assertThat(exists).isTrue();
    }

    @Test
    void existsMember_noExists() {
        //given
        int invalidMemberId = 999;
        //when
        boolean exists = memberRepository.existsMember(invalidMemberId);
        //then
        assertThat(exists).isFalse();
    }

    @Test
    void getMembers() {
        //given
        Member member1 = new Member("Jaejin", "jjlim01@mz.co.kr");
        Member member2 = new Member("Jaejin", "jjlim02@mz.co.kr");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        //when
        List<Member> resultMembers = memberRepository.getMembers();
        //then
        assertThat(resultMembers).hasSize(2);
    }

    @Test
    void getMembersByEmail() {
        //given
        Member member1 = new Member("Jaejin", "jjlim01@mz.co.kr");
        Member member2 = new Member("Jaejin", "jjlim02@mz.co.kr");
        Member member3 = new Member("Jaejin", "jjlim03@mz.co.kr");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        memberJpaRepository.save(member3);
        //when
        Optional<Member> resultMember = memberRepository.getMemberByEmail(member1.getEmail());
        //then
        assertThat(resultMember).isPresent();
    }

    @ParameterizedTest
    @CsvSource(value = {
        "Jaejin:jjlim01@mz.co.kr:1",
        "Jaejin::2",
        ":jjlim02@mz.co.kr:1",
        "::4"
    }, delimiter = ':')
    void getMembersByCond(String name, String email, int expectedSize) {
        //given
        Member member1 = new Member("Jaejin", "jjlim01@mz.co.kr");
        Member member2 = new Member("Jaejin", "jjlim02@mz.co.kr");
        Member member3 = new Member("Jaejin22", "jjlim03@mz.co.kr");
        Member member4 = new Member("Jaejin22", "jjlim04@mz.co.kr");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        memberJpaRepository.save(member3);
        memberJpaRepository.save(member4);

        MemberSearchCond cond = MemberSearchCond.builder()
            .name(name)
            .email(email)
            .build();

        //when
        List<Member> resultMembers = memberRepository.getMembersByCond(cond);
        //then
        assertThat(resultMembers).hasSize(expectedSize);
    }

    @Test
    void joinMember() {
        //given
        Member member = new Member("Jaejin", "jjlim@mz.co.kr");
        //when
        memberRepository.joinMember(member);
        //then
        Optional<Member> resultMember = memberJpaRepository.findById(member.getId());
        assertThat(resultMember).isPresent().contains(member);
    }

    @Test
    void leaveMember() {
        //given
        Member member = new Member("Jaejin", "jjlim@mz.co.kr");
        memberJpaRepository.save(member);
        //when
        memberRepository.leaveMember(member.getId());
        //then
        Optional<Member> resultMember = memberJpaRepository.findById(member.getId());
        assertThat(resultMember).isEmpty();
    }
}