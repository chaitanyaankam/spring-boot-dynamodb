package com.example.hazelcaset.dynamo.service;

import com.example.hazelcaset.dynamo.model.Music;
import com.example.hazelcaset.dynamo.repository.MusicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BasicService {

    @Autowired
    private MusicRepository musicRepository;

    Logger LOGGER = LoggerFactory.getLogger(BasicService.class);

    @Cacheable(key = "#artist", value = "music-cache")
    public Music findMusic(String artist) {
        LOGGER.info("fetching from the database.......");
        Music music = musicRepository.findByArtist(artist);
        LOGGER.info("data found in the repository {} ", music);
        return music;
    }

    public void saveEntity() {
        Music entity = new Music();
        entity.setArtist("Chaitanya");
        entity.setSong("Chaitanya Music");

        Music entity1 = new Music();
        entity1.setArtist("Harshu");
        entity1.setSong("Harshu Music");

        musicRepository.save(entity);
        musicRepository.save(entity1);
        LOGGER.info("data saved successfully");
    }

    public List<Music> findAll() {
        return StreamSupport.stream(musicRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
