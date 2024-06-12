package com.example.comuneids2024.Repository;

import com.example.comuneids2024.Model.Itinerary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItineraryRepository extends MongoRepository<Itinerary, Long> {
}
