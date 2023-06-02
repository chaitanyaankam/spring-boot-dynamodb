package com.example.hazelcaset.dynamo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CacheRestCallsService {
    Logger LOGGER = LoggerFactory.getLogger(CacheRestCallsService.class);

    @Cacheable(key="#otherFacts", value = "default-rest-cache")
    public List<String> findAllUsingRestServiceCall(List<Object> otherFacts) {
        LOGGER.info("fetching from the REST call.......");
        List<String> resultFromAPICall = List.of("active pharmacy 1", "active pharmacy 2");
        LOGGER.info("data from the rest call {} ", resultFromAPICall);
        return resultFromAPICall;
    }

    public void evictActivePharmaciesCache() {
        LOGGER.info("emptying REST cache...");
        cache.invalidate();
    }
}
