package com.lguplus.fleta.service.sample;

import com.lguplus.fleta.data.type.CacheNameType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SampleMemberServiceStep {

    private final SampleMemberDomainService memberDomainService;

    @Cacheable(cacheNames = CacheNameType.TTL_1, key = "'SUB_LOGIC_03_04'")
    public String doSubLogic03And04() {
        // Step 3
        String subLogic03 = memberDomainService.doSubLogic03();
        // Step 4
        String subLogic04 = memberDomainService.doSubLogic04();
        return subLogic03 + subLogic04;
    }
}
