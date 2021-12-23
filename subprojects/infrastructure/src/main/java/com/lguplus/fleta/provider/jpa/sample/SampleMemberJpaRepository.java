package com.lguplus.fleta.provider.jpa.sample;

import com.lguplus.fleta.data.entity.sample.SampleMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleMemberJpaRepository extends JpaRepository<SampleMember, String> {

//    @Modifying
//    @Query(value = "", nativeQuery = true)
//    void updateNameAndEmail();

}
