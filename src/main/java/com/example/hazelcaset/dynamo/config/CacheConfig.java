package com.example.hazelcaset.dynamo.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Autowired
    private CachePropertiesConfig cachePropertiesConfig;

    @Bean
    public Config hazelCastConfig(){
        Config config = new Config();
        config.setClusterName("distributed-demo-cluster");
        config.getNetworkConfig()
                .setPort(5701).setPortCount(3).setPortAutoIncrement(true)
                .getJoin()
                .getMulticastConfig().setEnabled(true);

        cachePropertiesConfig
                .getCaches()
                .forEach(cacheConfig -> {
                    config.addMapConfig(new MapConfig()
                            .setName(cacheConfig.getName())
                            .setTimeToLiveSeconds(cacheConfig.getTtl())
                            .setEvictionConfig(new EvictionConfig()
                                    .setSize(cacheConfig.getRecordsInMemory())
                                    .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                                    .setEvictionPolicy(EvictionPolicy.LRU)));
        });
        return config;
    }

    @Bean
    public HazelcastInstance hazelcastInstance(Config hazelCastConfig) {
        return Hazelcast.newHazelcastInstance(hazelCastConfig);
    }

    @Bean
    CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
        return new HazelcastCacheManager(hazelcastInstance);
    }

}
