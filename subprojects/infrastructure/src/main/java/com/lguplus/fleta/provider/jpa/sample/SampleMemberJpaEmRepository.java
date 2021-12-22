package com.lguplus.fleta.provider.jpa.sample;

import com.lguplus.fleta.data.entity.SampleMember;
import com.lguplus.fleta.data.entity.SampleTeam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SampleMemberJpaEmRepository {

    @PersistenceContext
    private final EntityManager em;

    public Optional<SampleMember> findMemberById(int memberId) {
        String jpql = "select m from SampleMember m where m.id = :memberId";
        SampleMember member = em.createQuery(jpql, SampleMember.class)
            .setParameter("memberId", memberId)
            .getSingleResult();
        return Optional.ofNullable(member);
    }

    public void saveTeam(SampleTeam team) {
        em.persist(team);
    }

    public void saveMember(SampleMember member) {
        em.persist(member);
    }
}
