package com.lguplus.fleta.service.sample;

import com.lguplus.fleta.data.dto.sample.SampleCustomMemberDto;
import com.lguplus.fleta.data.entity.SampleMember;
import com.lguplus.fleta.data.entity.SampleTeam;
import com.lguplus.fleta.data.mapper.SampleCustomMemberMapper;
import com.lguplus.fleta.repository.SampleMemberRepository;
import com.lguplus.fleta.repository.SampleTeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SampleMemberDomainService {

    private final SampleTeamRepository teamRepository;
    private final SampleMemberRepository memberRepository;
    private final SampleCustomMemberMapper customMemberMapper;

    public void initData() {
        SampleTeam team1 = new SampleTeam("team01");
        SampleTeam team2 = new SampleTeam("team02");
        teamRepository.create(team1);
        teamRepository.create(team2);

        SampleMember member1 = new SampleMember("name01", "name01@uplus.com");
        member1.setTeam(team1);
        SampleMember member2 = new SampleMember("name02", "name02@uplus.com");
        member2.setTeam(team1);
        SampleMember member3 = new SampleMember("name03", "name03@uplus.com");
        member3.setTeam(team2);
        memberRepository.join(member1);
        memberRepository.join(member2);
        memberRepository.join(member3);
    }

    @Cacheable(cacheNames = "MEMBER:")
    public SampleCustomMemberDto getMember(int memberId) {
        Optional<SampleMember> member = memberRepository.getMember(memberId);
        member.ifPresent(m -> log.debug(">>> member : {}", m));
        return member.map(m -> {
            log.debug(">>> member : {}", m);
            return customMemberMapper.toDto(m);
        }).orElse(null);
    }
}
