package com.example.hazelcaset.dynamo.controller;

import com.example.hazelcaset.dynamo.model.Music;
import com.example.hazelcaset.dynamo.service.BasicService;
import com.example.hazelcaset.dynamo.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BasicController {

    Logger LOGGER = LoggerFactory.getLogger(BasicController.class);

    @Autowired
    private BusinessService businessService;

    @Autowired
    private BasicService basicService;

    @GetMapping("/")
    public ResponseEntity<String> saveMusic() {
        LOGGER.info("saving using saveEntity ....");
        basicService.saveEntity();
        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/find")
    public List<Music> findAll() {
        LOGGER.info("finding all ....");
        return basicService.findAll();
    }

    @GetMapping("/artist/{artist}")
    public @ResponseBody Music getMusic(@PathVariable("artist") String artist) {
        LOGGER.info("fetching using findEntity ....");
        return businessService.getMusic(artist);
    }
}
