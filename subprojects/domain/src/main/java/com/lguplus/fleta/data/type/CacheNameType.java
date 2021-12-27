package com.lguplus.fleta.data.type;

import lombok.Getter;

import java.time.Duration;

public enum CacheNameType {

    TTL_1_MINUTE(Constant.TTL_1_MINUTE, Duration.ofMinutes(1)),
    TTL_5_MINUTE(Constant.TTL_5_MINUTE, Duration.ofMinutes(5)),
    TTL_10_MINUTE(Constant.TTL_10_MINUTE, Duration.ofMinutes(10)),
    TTL_15_MINUTE(Constant.TTL_15_MINUTE, Duration.ofMinutes(15)),
    TTL_30_MINUTE(Constant.TTL_30_MINUTE, Duration.ofMinutes(30)),
    TTL_1_HOUR(Constant.TTL_1_HOUR, Duration.ofHours(1)),
    TTL_2_HOUR(Constant.TTL_2_HOUR, Duration.ofHours(2)),
    TTL_1_DAY(Constant.TTL_1_DAY, Duration.ofDays(1)),
    TTL_2_DAY(Constant.TTL_2_DAY, Duration.ofDays(2));

    @Getter
    private final String cacheName;
    @Getter
    private final Duration duration;

    CacheNameType(String cacheName, Duration duration) {
        this.cacheName = cacheName;
        this.duration = duration;
    }

    @Override public String toString() {
        return super.toString();
    }


    public static class Constant {

        public static final String TTL_1_MINUTE = "TTL1M";
        public static final String TTL_5_MINUTE = "TTL5M";
        public static final String TTL_10_MINUTE = "TTL10M";
        public static final String TTL_15_MINUTE = "TTL15M";
        public static final String TTL_30_MINUTE = "TTL30M";
        public static final String TTL_1_HOUR = "TTL1H";
        public static final String TTL_2_HOUR = "TTL2H";
        public static final String TTL_1_DAY = "TTL1D";
        public static final String TTL_2_DAY = "TTL2D";

        private Constant() { }
    }
}
