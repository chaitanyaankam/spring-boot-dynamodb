package com.example.hazelcaset.dynamo.controller;

import com.example.hazelcaset.dynamo.service.CacheRestCallsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/evict/cache/")
public class CacheController {

    @Autowired
    private CacheRestCallsService activePharmacy;

    @GetMapping("/activePharmaciesCache")
    public @ResponseBody String getInMemoryResult() {
        LOGGER.info("Clearing activePharmacies cache");
        cacheRestCallsService.evictActivePharmaciesCache();
        return "Success";
    }
}
