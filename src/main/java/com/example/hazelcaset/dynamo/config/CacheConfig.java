package com.example.hazelcaset.dynamo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {
    @Value("${spring.cache.rest-cache.name}")
    private String restCacheName;

    @Autowired
    private CachePropertiesConfig cachePropertiesConfig;

    @Bean
    public CacheManager inMemoryCacheManager() {
        return new ConcurrentMapCacheManager(restCacheName);
    }
}
