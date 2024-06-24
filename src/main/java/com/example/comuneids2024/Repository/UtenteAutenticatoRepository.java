package com.example.comuneids2024.Repository;

import com.example.comuneids2024.Model.Utente;
import com.example.comuneids2024.Model.UtenteAutenticato;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteAutenticatoRepository extends MongoRepository<UtenteAutenticato, Long> {

    UtenteAutenticato findByUsername(String username);
}
