package com.lguplus.fleta.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@TestConfiguration
public class TestConfig {

    @MockBean
    RedisConnectionFactory redisConnectionFactory;
}
