package com.lguplus.fleta.provider.jpa.sample;

import com.lguplus.fleta.data.entity.SampleMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleMemberJpaRepository extends JpaRepository<SampleMember, Integer> {
}
