package com.example.comuneids2024.Repository;

import com.example.comuneids2024.Model.Contest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestRepository extends MongoRepository<Contest,Long> {
}
