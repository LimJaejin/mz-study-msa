package com.lguplus.fleta.domain.study;

import com.lguplus.fleta.BootConfig;
import com.lguplus.fleta.data.entity.study.Member;
import com.lguplus.fleta.provider.jpa.study.MemberJpaRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@SpringBootTest(classes = BootConfig.class)
@Transactional // 스프링 부트 테스트에서 디폴트 : Rollback
class MemberRepositoryImplSpringBootTest {

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
    void getMembers() {
    }

    @Test
    void getMembersByEmail() {
    }

    @Test
    void getMembersByCond() {
    }

    @Test
    void joinMember() {
    }

    @Test
    void leaveMember() {
    }
}