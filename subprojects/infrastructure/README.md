Infrastructure Layer
===
## 개요

+ 기술스택 의존성이 강한 구현체 제공
+ JPA Repository, HTTP 통신(FeignClient), Redis, Kafka(Spring Cloud Stream), gRPC ,mm7 등
___
## 주요클래스
### RepositoryImpl
+ domain 레이어에서 DB 데이터 핸들링을 위해 선언한 인터페이스를 구현합니다.
+ JpaRepository 인터페이스 및 JpaEmRepository 클래스를 주입하여 사용합니다.
```
package com.lguplus.fleta.provider.jpa.sample;

import com.lguplus.fleta.data.entity.custom.sample.SampleEntityDto;
import com.lguplus.fleta.data.entity.sample.SampleMember;
import com.lguplus.fleta.data.entity.sample.SampleMemberMapp;
import com.lguplus.fleta.data.entity.sample.SampleQueryConditonDto;
import com.lguplus.fleta.repository.sample.SampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 도메인 레이어의 SampleRepository 인터페이스 구현 클래스
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class SampleRepositoryImpl implements SampleRepository {

    private final SampleMemberJpaRepository sampleMemberJpaRepository;
    private final SampleMemberMappJpaRepository sampleMemberMappJpaRepository;
    private final SampleJpaEmRepository sampleJpaEmRepository;

    @Override
    public SampleMember create(SampleMember entity) {
        return this.sampleMemberJpaRepository.save(entity);
    }

    @Override
    public SampleMemberMapp create(SampleMemberMapp entity) {
        return this.sampleMemberMappJpaRepository.save(entity);
    }

    @Override
    public List<SampleEntityDto> findSamples(SampleQueryConditonDto queryConditonDto) {
        return this.sampleJpaEmRepository.findSamples(queryConditonDto);
    }

}
```
___
### JpaRepository
+ domain 레이어에서 작성한 DB 테이블과 1:1로 매칭되는 entity를 처리하는 spring data JPA의 repository 인터페이스를 선언합니다.
+ org.springframework.data.jpa.repository.JpaRepository를 상속합니다.
  + entity에 대한 cud 처리시 spring data JPA의 JpaRepository에서 제공하는 save, deleteById 등을 사용합니다.
```
package com.lguplus.fleta.provider.jpa.sample;

import com.lguplus.fleta.data.entity.sample.SampleMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleMemberJpaRepository extends JpaRepository<SampleMember, String> {

}
```
___
### EmRepository
+ EntityManager를 주입 후 native query로 처리하기 위한 클래스입니다. 아래와 같은 경우 EntityManager의 native query로 처리합니다.
  + join query를 사용하는 경우
  + 동적 query를 사용하는 경우
  + 리턴하는 칼럼이 단일 테이블과 1:1로 매칭되는 entity의 속성과 다른 경우
+ query 상단에  쿼리 아이디를 주석으로 작성합니다.
```
package com.lguplus.fleta.provider.jpa.sample;

import com.lguplus.fleta.data.entity.custom.sample.SampleEntityDto;
import com.lguplus.fleta.data.entity.sample.SampleQueryConditonDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class SampleJpaEmRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private static final String CR = "\n";

    private static final String SAMPLE_QUERY = "/* SVC.MsaBoilerplate.SampleEmRepository.findSamples.01 */" + CR + CR +
            "SELECT SM.*, SMM.* " + CR +
            "FROM imcsuser.sample_member SM JOIN imcsuser.sample_member_mapp SMM ON SM.id = SMM.sample_member_id " + CR;

    private static final String BAD_SAMPLE_QUERY = "/* SVC.MsaBoilerplate.SampleEmRepository.findSamples.02 */" + CR + CR +
            "SELECT bad_column, SM.*, SMM.* " + CR +
            "FROM imcsuser.sample_member SM JOIN imcsuser.sample_member_mapp SMM ON SM.id = SMM.sample_member_id " + CR;

    /**
     * 샘플멤버 & 샘플멤버맵 조인 조회
     * 
     * @param queryConditonDto
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SampleEntityDto> findSamples(SampleQueryConditonDto queryConditonDto) {
        String sql = this.getSampleSql(queryConditonDto, "ORDER BY SM.reg_date DESC");
        Query query = this.entityManager.createNativeQuery(sql, SampleEntityDto.class);
        return this.setSampleQuery(queryConditonDto, query).getResultList();
    }

    private String getAndOrEmpty(StringBuilder whereClause) {
        return StringUtils.isNotBlank(whereClause.toString()) ? "AND " : StringUtils.EMPTY;
    }

    /**
     * 동적 where 조건 처리
     *
     * @param queryConditonDto
     * @param sortCondition
     * @return
     */
    private String getSampleSql(SampleQueryConditonDto queryConditonDto, String sortCondition) {
        StringBuilder whereClause = new StringBuilder();

        if (StringUtils.isNotBlank(queryConditonDto.getName())) {
            whereClause.append("SM.name LIKE :name ").append(CR);
        }
        if (StringUtils.isNotBlank(queryConditonDto.getEmail())) {
            whereClause.append(this.getAndOrEmpty(whereClause)).append("SM.email LIKE :email ").append(CR);
        }
        if (StringUtils.isNotBlank(queryConditonDto.getSaId())) {
            whereClause.append(this.getAndOrEmpty(whereClause)).append("SMM.sa_id = :saId ").append(CR);

            if (StringUtils.isNotBlank(queryConditonDto.getStbMac())) {
                whereClause.append(this.getAndOrEmpty(whereClause)).append("SMM.stb_mac = :stbMac ").append(CR);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(SAMPLE_QUERY);

        if (StringUtils.isNotBlank(whereClause.toString())) {
            sb.append("WHERE ").append(whereClause);
        }

        if (StringUtils.isNotBlank(sortCondition)) {
            sb.append(sortCondition);
        }

        return sb.toString();
    }

    /**
     * 동적 where 조건절에 전달할 파라미터 처리
     *
     * @param queryConditonDto
     * @param query
     * @return
     */
    private Query setSampleQuery(SampleQueryConditonDto queryConditonDto, Query query) {
        if (StringUtils.isNotBlank(queryConditonDto.getName())) {
            query.setParameter("name", "%" + queryConditonDto.getName() + "%");
        }
        if (StringUtils.isNotBlank(queryConditonDto.getEmail())) {
            query.setParameter("email", "%" + queryConditonDto.getEmail() + "%");
        }
        if (StringUtils.isNotBlank(queryConditonDto.getSaId())) {
            query.setParameter("saId", queryConditonDto.getSaId());

            if (StringUtils.isNotBlank(queryConditonDto.getStbMac())) {
                query.setParameter("stbMac", queryConditonDto.getStbMac());
            }
        }

        return query;
    }

}
```
___
### ClientImpl
+ domain 레이어에서 다른 MSA 도메인과의 통신을 위해 선언한 인터페이스의 구현체입니다.
+ FeignClient 인터페이스를 참조합니다.
```
package com.lguplus.fleta.provider.rest;

import com.lguplus.fleta.client.SubscriberInfoClient;
import com.lguplus.fleta.data.dto.common.subscriber.SubscriberInfoDto;
import com.lguplus.fleta.data.dto.response.InnerResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SubscriberInfoClientImpl implements SubscriberInfoClient {

    private final SubscriberFeignClient subscriberFeignClient;

    @Override
    public InnerResponseDto<SubscriberInfoDto> findBySaIdAndStbMac(String saId, String stbMac) {
        return this.subscriberFeignClient.findSubscriberInfo(saId, stbMac);
    }

}
```
___
## 일반적인 infrastructure 레이어 개발
+ 클래스 및 메소드 상단에 @Transactional 어노테이션을 선언하지 않습니다.
  + 트랜잭션은 application 레이어에서 비즈니스 로직 단위로 선언하여 관리하도록 합니다.
+ 가능한 메소드 내부에서 try ~ catch로 에러를 핸들링하지 않도록 구현합니다.
  + 스프링 프레임워크는 데이터 액세스 계층의 구체적인 예외를 DataAccessException, PersistenceException 등의 추상화한 예외로 포장하여 발생시키기 때문에 여기서 발생한 오류는 도메인 레이어에서 별도로 정의한 unchecked exception으로 처리합니다.
  + FeignClient는 HTTP 통신 시 발생한 구체적인 예외를 FeignException으로 포장, 추상화하여 발생시키기 때문에 여기서 발생한 오류는 도메인 레이어에서 별도로 정의한 unchecked exception으로 처리합니다.

