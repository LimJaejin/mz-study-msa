package com.lguplus.fleta.provider.jpa.sample;

import com.lguplus.fleta.data.entity.SampleTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleTeamJpaRepository extends JpaRepository<SampleTeam, Integer> {
}
