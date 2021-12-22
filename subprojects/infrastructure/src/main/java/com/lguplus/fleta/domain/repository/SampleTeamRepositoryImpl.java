package com.lguplus.fleta.domain.repository;

import com.lguplus.fleta.data.entity.SampleTeam;
import com.lguplus.fleta.provider.jpa.sample.SampleMemberJpaEmRepository;
import com.lguplus.fleta.provider.jpa.sample.SampleTeamJpaRepository;
import com.lguplus.fleta.repository.SampleTeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SampleTeamRepositoryImpl implements SampleTeamRepository {

    private final SampleTeamJpaRepository teamJpaRepository;
    private final SampleMemberJpaEmRepository memberJpaEmRepository;

    @Override
    public Optional<SampleTeam> getTeam(int teamId) {
        Optional<SampleTeam> team = teamJpaRepository.findById(teamId);
        team.ifPresent(t -> log.debug(">>> team : {}", t));
        return team;
    }

    @Override
    public void create(SampleTeam team) {
        log.debug(">>> team before : {}", team);
        SampleTeam createdTeam = teamJpaRepository.save(team);
        log.debug(">>> team after  : {}", team);
        log.debug(">>> createdTeam : {}", createdTeam);
    }

    @Override
    public void remove(int teamId) {
        teamJpaRepository.deleteById(teamId);
    }
}
