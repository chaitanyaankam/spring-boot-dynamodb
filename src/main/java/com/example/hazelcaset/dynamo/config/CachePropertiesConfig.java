package com.example.hazelcaset.dynamo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "hazelcast")
public class CachePropertiesConfig {

    private List<Cache> caches;

    public List<Cache> getCaches() {
        return caches;
    }

    public void setCaches(List<Cache> caches) {
        this.caches = caches;
    }

    public static class Cache {
        private String name;
        private int ttl;
        private int recordsInMemory;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTtl() {
            return ttl;
        }

        public void setTtl(int ttl) {
            this.ttl = ttl;
        }

        public int getRecordsInMemory() {
            return recordsInMemory;
        }

        public void setRecordsInMemory(int recordsInMemory) {
            this.recordsInMemory = recordsInMemory;
        }
    }
}
