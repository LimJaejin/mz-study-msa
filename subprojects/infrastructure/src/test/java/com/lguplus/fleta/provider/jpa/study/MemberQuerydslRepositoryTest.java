package com.lguplus.fleta.provider.jpa.study;

import com.lguplus.fleta.data.dto.study.MemberSearchCond;
import com.lguplus.fleta.data.entity.study.Member;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import static com.lguplus.fleta.data.entity.study.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class MemberQuerydslRepositoryTest {

    @InjectMocks MemberQuerydslRepository memberQuerydslRepository;
    @Mock JPAQueryFactory queryFactory;
    @Mock JPAQuery<Member> memberQuery;
    @Mock JPAQuery<Long> countQuery;

    @ParameterizedTest
    @CsvSource(value = {
        "NAME:EMAIL",
        "NAME:",
        ":EMAIL",
        ":"
    }, delimiter = ':')
    void getMembers(String condName, String condEmail) {
        //given
        List<Member> members = List.of();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Member> pageMember = new PageImpl<>(members, pageable, 0);
        getMembers_methodMocking(members, pageable, pageMember);
        MemberSearchCond cond = MemberSearchCond.builder().name(condName).email(condEmail).build();
        //when
        Page<Member> result = memberQuerydslRepository.getMembers(cond, pageable);
        //then
        assertThat(result).isEqualTo(pageMember);
    }

    private void getMembers_methodMocking(List<Member> members, Pageable pageable, Page<Member> pageMember) {
        given(queryFactory.selectFrom(member)).willReturn(memberQuery);
        given(memberQuery.where(any(), any())).willReturn(memberQuery);
        given(memberQuery.offset(anyLong())).willReturn(memberQuery);
        given(memberQuery.limit(anyLong())).willReturn(memberQuery);
        given(memberQuery.fetch()).willReturn(members);

        given(queryFactory.select(member.count())).willReturn(countQuery);
        given(countQuery.from(member)).willReturn(countQuery);
        given(countQuery.where(any(), any())).willReturn(countQuery);

        try (MockedStatic<PageableExecutionUtils> mockedStatic = mockStatic(PageableExecutionUtils.class)) {
            mockedStatic.when(() -> PageableExecutionUtils.getPage(members, pageable, countQuery::fetchOne))
                .thenReturn(pageMember);
        }
    }
}
