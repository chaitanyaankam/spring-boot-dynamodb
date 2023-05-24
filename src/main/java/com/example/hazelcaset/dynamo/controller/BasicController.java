package com.example.hazelcaset.dynamo.controller;

import com.example.hazelcaset.dynamo.service.CacheRestCallsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BasicController {
    Logger LOGGER = LoggerFactory.getLogger(BasicController.class);

    @Autowired
    private CacheRestCallsService cacheRestCallsService;

    @GetMapping("/inMemory")
    public @ResponseBody List<String> getInMemoryResult() {
        LOGGER.info("find All Using Rest Service Call ....");
        List<String> otherFacts = new ArrayList<>();
        otherFacts.add("Test");
        return cacheRestCallsService.findAllUsingRestServiceCall(otherFacts);
    }
}
