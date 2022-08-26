package com.lguplus.fleta.provider.jpa.study;

import com.lguplus.fleta.data.dto.study.MemberSearchCond;
import com.lguplus.fleta.data.entity.study.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class MemberJpaEmRepository {

    private final EntityManager em;

    public List<Member> getMembersByCond(MemberSearchCond cond) {
        String name = cond.getName();
        String email = cond.getEmail();

        Map<String, Object> parameters = new HashMap<>();
        String jpql = "select m from Member m";

        if (StringUtils.hasText(name) && StringUtils.hasText(email)) {
            jpql += " where m.name = :name and m.email = :email";
            parameters.put("name", name);
            parameters.put("email", email);
        }
        if (StringUtils.hasText(name) && !StringUtils.hasText(email)) {
            jpql += " where m.name = :name";
            parameters.put("name", name);
        }
        if (!StringUtils.hasText(name) && StringUtils.hasText(email)) {
            jpql += " where m.email = :email";
            parameters.put("email", email);
        }

        TypedQuery<Member> query = em.createQuery(jpql, Member.class);
        parameters.forEach(query::setParameter);
        return query.getResultList();
    }
}
