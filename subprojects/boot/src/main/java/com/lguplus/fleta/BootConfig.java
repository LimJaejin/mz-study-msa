package com.lguplus.fleta;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Slf4j
@Configuration
@ComponentScan(basePackages = "com.lguplus.fleta")
public class BootConfig {

    /**
     * Amazon Aurora PostgreSQL 모범 사례 - DNS 캐시 제한 시간 설정
     * @link https://docs.aws.amazon.com/ko_kr/AmazonRDS/latest/AuroraUserGuide/AuroraPostgreSQL.BestPractices.html
     */
    @EventListener(ApplicationStartedEvent.class)
    public void setNetworkAddrCacheTtlConfig() {
        // TTL 값, 30초 미만 설정
        java.security.Security.setProperty("networkaddress.cache.ttl", "1");
        // If the lookup fails, default to something like small to retry
        java.security.Security.setProperty("networkaddress.cache.negative.ttl", "3");

        log.info(">>> networkaddress.cache.ttl: {}, networkaddress.cache.negative.ttl: {}",
            java.security.Security.getProperty("networkaddress.cache.ttl"),
            java.security.Security.getProperty("networkaddress.cache.negative.ttl")
        );
    }
}
