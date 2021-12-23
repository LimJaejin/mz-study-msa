package com.lguplus.fleta.provider.jpa.sample;

import com.lguplus.fleta.data.entity.sample.SampleMemberMapp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleMemberMappJpaRepository extends JpaRepository<SampleMemberMapp, String> {
}
