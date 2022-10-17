package com.lguplus.fleta.provider.jpa.study;

import com.lguplus.fleta.data.dto.study.MemberSearchCond;
import com.lguplus.fleta.data.entity.study.Member;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "com.lguplus.fleta.config")
class MemberQuerydslRepositoryDataJpaTest {

    @Autowired EntityManager em;
    @Autowired MemberQuerydslRepository memberQuerydslRepository;

    @ParameterizedTest
    @CsvSource(value = {
        "Jaejin:jjlim@mz.co.kr:1",
        "Jaejin::2",
        ":jjlim@mz.co.kr:1",
        "::2"
    }, delimiter = ':')
    void basic(String condName, String condEmail, int resultSize) {
        Member member1 = new Member("Jaejin", "jjlim@mz.co.kr", 20);
        Member member2 = new Member("Jaejin", "jjlim2@mz.co.kr", 30);
        em.persist(member1);
        em.persist(member2);

        MemberSearchCond cond = MemberSearchCond.builder()
            .name(condName)
            .email(condEmail)
            .build();
        Pageable pageable = PageRequest.of(0, 20);

        Page<Member> membersWithPage = memberQuerydslRepository.getMembers(cond, pageable);
        List<Member> members = membersWithPage.getContent();
        int totalPage = membersWithPage.getTotalPages();
        long totalCount = membersWithPage.getTotalElements();

        assertThat(members).hasSize(resultSize);
        assertThat(totalPage).isEqualTo(1);
        assertThat(totalCount).isEqualTo(resultSize);
    }
}