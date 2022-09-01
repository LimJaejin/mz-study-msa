package com.lguplus.fleta.provider.jpa.study;

import com.lguplus.fleta.data.entity.study.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByEmail(String email);

    List<Member> findByName(String name);

    List<Member> findByNameAndEmail(String name, String email);
}
