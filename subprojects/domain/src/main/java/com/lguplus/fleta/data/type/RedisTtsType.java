package com.lguplus.fleta.data.type;

public enum RedisTtsType {
    TTL_1(1),
    TTL_5(5),
    TTL_10(10),
    TTL_15(15),
    TTL_30(30),
    TTL_60(60);

    private final long value;

    RedisTtsType(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
