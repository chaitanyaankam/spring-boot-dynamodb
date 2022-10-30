package com.example.hazelcaset.dynamo.service;

import com.example.hazelcaset.dynamo.model.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {

    @Autowired
    BasicService basicService;

    public Music getMusic(String artist) {
        return basicService.findMusic(artist);
    }
}
