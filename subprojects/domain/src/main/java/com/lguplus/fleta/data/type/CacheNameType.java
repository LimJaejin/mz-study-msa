package com.lguplus.fleta.data.type;

import lombok.Getter;

import java.time.Duration;

public enum CacheNameType {

    TTL_1_MINUTE("TTL1M", Duration.ofMinutes(1)),
    TTL_5_MINUTE("TTL5M", Duration.ofMinutes(5)),
    TTL_10_MINUTE("TTL10M", Duration.ofMinutes(10)),
    TTL_15_MINUTE("TTL15M", Duration.ofMinutes(15)),
    TTL_30_MINUTE("TTL30M", Duration.ofMinutes(30)),
    TTL_1_HOUR("TTL1H", Duration.ofHours(1)),
    TTL_2_HOUR("TTL2H", Duration.ofHours(2)),
    TTL_1_DAY("TTL1D", Duration.ofDays(1)),
    TTL_2_DAY("TTL2D", Duration.ofDays(2));

    @Getter
    private final String cacheName;
    @Getter
    private final Duration duration;

    CacheNameType(String cacheName, Duration duration) {
        this.cacheName = cacheName;
        this.duration = duration;
    }
}
