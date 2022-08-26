package com.lguplus.fleta.provider.jpa.study;

import com.lguplus.fleta.data.dto.study.MemberSearchCond;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberJpaEmRepositoryTest {

    @InjectMocks MemberJpaEmRepository memberJpaEmRepository;
    @Mock EntityManager em;
    @Mock TypedQuery typedQuery;

    @ParameterizedTest
    @CsvSource(value = {
        "Jaejin:jjlim@mz.co.kr",
        "Jaejin:",
        ":jjlim@mz.co.kr",
        ":"
    }, delimiter = ':')
    void getMembersByCond(String name, String email) {
        //given
        //when(em.createQuery(any(), any())).thenReturn(typedQuery);
        given(em.createQuery(any(), any())).willReturn(typedQuery);

        MemberSearchCond cond = MemberSearchCond.builder()
            .name(name)
            .email(email)
            .build();
        //when
        memberJpaEmRepository.getMembersByCond(cond);
        //then
    }

}