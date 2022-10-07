package com.lguplus.fleta.service.study;

import com.lguplus.fleta.data.dto.study.MemberSearchCond;
import com.lguplus.fleta.data.entity.study.Member;
import com.lguplus.fleta.exception.study.member.DuplicateMemberEmailException;
import com.lguplus.fleta.exception.study.member.InvalidMemberInfoException;
import com.lguplus.fleta.exception.study.member.NotExistsMemberException;
import com.lguplus.fleta.repository.study.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDomainService {

    private static final String NOT_EXISTS_MEMBER_MESSAGE = "존재하지 않는 회원입니다.";
    private static final String INVALID_MEMBER_INFO_MESSAGE = "유효하지 않은 회원정보입니다.";
    private static final String DUPLICATE_MEMBER_EMAIL_MESSAGE = "회원 이메일이 이미 존재합니다.";

    private final MemberRepository memberRepository;

    public Member getMember(int memberId) {
        return memberRepository.getMember(memberId)
            .orElseThrow(() -> new NotExistsMemberException(NOT_EXISTS_MEMBER_MESSAGE));
    }

    public List<Member> getMembers(MemberSearchCond cond) {
        return memberRepository.getMembersByCond(cond);
    }

    public void joinMember(Member member) {
        validateMemberInfo(member);
        memberRepository.joinMember(member);
    }

    private void validateMemberInfo(Member member) {
        // 회원정보가 불충분한 경우
        if (!member.validate()) {
            throw new InvalidMemberInfoException(INVALID_MEMBER_INFO_MESSAGE);
        }
        // 이메일이 중복인 경우
        memberRepository.getMemberByEmail(member.getEmail())
            .ifPresent(m -> {
                throw new DuplicateMemberEmailException(DUPLICATE_MEMBER_EMAIL_MESSAGE);
            });
    }

    public void leaveMember(int memberId) {
        validateIfMemberExists(memberId);
        memberRepository.leaveMember(memberId);
    }

    private void validateIfMemberExists(int memberId) {
        if (!memberRepository.existsMember(memberId)) {
            throw new NotExistsMemberException(NOT_EXISTS_MEMBER_MESSAGE);
        }
    }
}
