package com.lguplus.fleta.provider.jpa.sample;

import com.lguplus.fleta.data.entity.SampleMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SampleMemberJpaRepository extends JpaRepository<SampleMember, Integer> {
}
