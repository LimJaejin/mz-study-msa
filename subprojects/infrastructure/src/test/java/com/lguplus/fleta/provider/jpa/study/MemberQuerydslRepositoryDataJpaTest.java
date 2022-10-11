package com.lguplus.fleta.provider.jpa.study;

import com.lguplus.fleta.data.dto.study.MemberSearchCond;
import com.lguplus.fleta.data.entity.study.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "com.lguplus.fleta.config")
class MemberQuerydslRepositoryDataJpaTest {

    @Autowired EntityManager em;
    @Autowired MemberQuerydslRepository memberQuerydslRepository;

    @TestConfiguration
    static class TestConfig {

        @Autowired EntityManager em;

        @Bean
        JPAQueryFactory jpaQueryFactory() {
            return new JPAQueryFactory(em);
        }
    }

    @Test
    void basic() {
        Member member1 = new Member("Jaejin", "jjlim@mz.co.kr", 20);
        Member member2 = new Member("Jaejin2", "jjlim2@mz.co.kr", 30);
        em.persist(member1);
        em.persist(member2);

        MemberSearchCond cond = MemberSearchCond.builder()
            .name("Jaejin")
            .email("jjlim@mz.co.kr")
            .build();
        Pageable pageable = PageRequest.of(0, 20);

        Page<Member> membersWithPage = memberQuerydslRepository.getMembers(cond, pageable);
        List<Member> members = membersWithPage.getContent();
        int totalPage = membersWithPage.getTotalPages();
        long totalCount = membersWithPage.getTotalElements();
    }
}