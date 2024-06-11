package com.example.comuneids2024.Repository;

import com.example.comuneids2024.Model.Content;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends MongoRepository<Content,String> {
}
