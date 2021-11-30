package com.lguplus.fleta.provider.jpa.sample;

import com.lguplus.fleta.data.entity.SampleMember;
import com.lguplus.fleta.data.entity.SampleTeam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SampleMemberJpaEmRepository {

    @PersistenceContext
    private final EntityManager em;

    public void saveTeam(SampleTeam team) {
    }

    public void saveMember(SampleMember member) {
    }
}
