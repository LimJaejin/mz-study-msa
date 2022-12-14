package com.lguplus.fleta.config;

import com.lguplus.fleta.data.type.CacheNameType;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

@Slf4j
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Value("${spring.application.name-abbr-env}")
    private String serviceNameAbbr;

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
            .prefixCacheNameWith(serviceNameAbbr + "::")  // Redis CacheName Prefix : 서비스명 약어 + "::"
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        // 리소스 유형에 따라 만료 시간을 다르게 지정
        Map<String, RedisCacheConfiguration> redisCacheConfigMap = new HashMap<>();

        // TTL 캐시이름 설정
        for (CacheNameType cacheNameType : CacheNameType.values()) {
            redisCacheConfigMap.put(cacheNameType.code(), redisCacheConfiguration.entryTtl(cacheNameType.getDuration()));
        }

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
