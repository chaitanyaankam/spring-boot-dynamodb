package com.example.hazelcaset.dynamo.repository;

import com.example.hazelcaset.dynamo.model.Music;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.repository.CrudRepository;

@EnableScan
@EnableScanCount
public interface MusicRepository extends CrudRepository<Music, String> {

    Music findByArtist(String name);
}
