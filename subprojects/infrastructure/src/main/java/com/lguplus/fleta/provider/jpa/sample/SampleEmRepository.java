package com.lguplus.fleta.provider.jpa.sample;

import com.lguplus.fleta.data.dto.sample.SampleQueryConditonDto;
import com.lguplus.fleta.data.entity.custom.sample.SampleEntityDto;
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
public class SampleEmRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private static final String CR = "\n";

    private static final String SAMPLE_QUERY = CR + "SELECT SM.*, SMM.* " + CR +
            "FROM imcsuser.sample_member SM JOIN imcsuser.sample_member_mapp SMM ON SM.id = SMM.sample_member_id " + CR;

    private static final String BAD_SAMPLE_QUERY = CR + "SELECT bad_column, SM.*, SMM.* " + CR +
            "FROM imcsuser.sample_member SM JOIN imcsuser.sample_member_mapp SMM ON SM.id = SMM.sample_member_id " + CR;

    /**
     * 샘플멤버 & 샘플멤버맵 조인 조회(db view를 일반 join문으로 변경)
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

        // for test
        if ("나쁜쿼리".equals(queryConditonDto.getName())) {
            sb.append(BAD_SAMPLE_QUERY);
        }

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
