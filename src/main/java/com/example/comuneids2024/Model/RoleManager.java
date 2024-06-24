package com.example.comuneids2024.Model;


import com.example.comuneids2024.Repository.UtenteAutenticatoRepository;
import com.example.comuneids2024.Model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
public class RoleManager {

    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;
    public void nuovoRuolo(Long utenteId, Role role) {
        Optional<UtenteAutenticato> utenteOpt = utenteAutenticatoRepository.findById(utenteId);

        if (utenteOpt.isPresent()) {
            UtenteAutenticato utente = utenteOpt.get();
            utente.setRole(role);
            utenteAutenticatoRepository.save(utente);
        } else {
            throw new RuntimeException("Utente non trovato");
        }
    }
}