package com.example.comuneids2024.Repository;

import com.example.comuneids2024.Model.Comune;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComuneRepository extends MongoRepository<Comune,String> {

    Comune findByName(String nome);
}
