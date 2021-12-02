package com.lguplus.fleta.config;

import com.lguplus.fleta.data.type.CacheNameType;
import com.lguplus.fleta.data.type.RedisTtsType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    /**
     * No CacheManager Bean
     */
    @Bean
    @Profile("test")
    public CacheManager noRedisCacheManager() {
        log.debug(">>> Redis Cache 미적용");
        return new NoOpCacheManager();
    }

    /**
     * CacheManager Bean
     */
    @Bean
    @Profile("!test")
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
            .defaultCacheConfig()
            .disableCachingNullValues()
            // 서비스 도메인 약어: https://lguplus-msa-dev.atlassian.net/wiki/spaces/LGUPLUSMSA/pages/888045703/RDBMS+Object+PostgreSQL
            // Redis CacheName Prefix : 서비스 도메인 약어 + "::"
            .prefixCacheNameWith("MB::")
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        // 리소스 유형에 따라 만료 시간을 다르게 지정
        Map<String, RedisCacheConfiguration> redisCacheConfigMap = new HashMap<>();

        // 만료시간 15분, 30분, 60분
        redisCacheConfigMap.put(CacheNameType.TTL_1, redisCacheConfiguration.entryTtl(Duration.ofMinutes(RedisTtsType.TTS_1.getValue())));
        redisCacheConfigMap.put(CacheNameType.TTL_10, redisCacheConfiguration.entryTtl(Duration.ofMinutes(RedisTtsType.TTS_10.getValue())));
        redisCacheConfigMap.put(CacheNameType.TTL_15, redisCacheConfiguration.entryTtl(Duration.ofMinutes(RedisTtsType.TTS_15.getValue())));
        redisCacheConfigMap.put(CacheNameType.TTL_30, redisCacheConfiguration.entryTtl(Duration.ofMinutes(RedisTtsType.TTS_30.getValue())));
        redisCacheConfigMap.put(CacheNameType.TTL_60, redisCacheConfiguration.entryTtl(Duration.ofMinutes(RedisTtsType.TTS_60.getValue())));

        log.debug(">>> Redis Cache 구성");

        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(redisConnectionFactory)
            .cacheDefaults(redisCacheConfiguration)
            .withInitialCacheConfigurations(redisCacheConfigMap)
            .build();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CustomRedisCacheErrorHandler();
    }

    @Slf4j
    public static class CustomRedisCacheErrorHandler implements CacheErrorHandler {

        @Override
        public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
            log.error("Unable to get from cache. key: {}, name: {} - {}", key, cache.getName(), e.getMessage());
        }

        @Override
        public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
            log.error("Unable to put into cache. key: {}, name: {} - {}", key, cache.getName(), e.getMessage());
        }

        @Override
        public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
            log.error("Unable to evict from cache. key: {}, name: {} - {}", key, cache.getName(), e.getMessage());
        }

        @Override
        public void handleCacheClearError(RuntimeException e, Cache cache) {
            log.error("Unable to clean cache. name: {} - {}", cache.getName(), e.getMessage());
        }
    }
}
