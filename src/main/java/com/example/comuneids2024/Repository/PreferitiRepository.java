package com.example.comuneids2024.Repository;

import com.example.comuneids2024.Model.UtenteAutenticato;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PreferitiRepository extends MongoRepository<UtenteAutenticato, Long> {
}
