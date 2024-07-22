package com.example.comuneids2024.Repository;

import com.example.comuneids2024.Model.POI;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface POIRepository extends MongoRepository<POI,String> {
}
