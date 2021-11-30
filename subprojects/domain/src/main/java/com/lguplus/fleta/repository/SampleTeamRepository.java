package com.lguplus.fleta.repository;

import com.lguplus.fleta.data.entity.SampleTeam;

import java.util.Optional;

public interface SampleTeamRepository {

    Optional<SampleTeam> getTeam(int teamId);

    void create(SampleTeam team);

    void remove(int teamId);
}
