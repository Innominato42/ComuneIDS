package com.example.comuneids2024.Model;


import com.example.comuneids2024.Repository.RichiestaRuoloRepository;
import com.example.comuneids2024.Repository.UtenteAutenticatoRepository;
import com.example.comuneids2024.Model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Component
public class RoleManager {

    @Autowired
    private UtenteAutenticatoManager utentiAutenticatiManager;

    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;

    @Autowired
    private RichiestaRuoloRepository richiestaRuoloRepository;

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

    public void nuovaRichiestaCambioRuolo(Long utenteId, Role ruolo) {
        RichiestaRuolo richiestaRuolo = new RichiestaRuolo(utenteId, ruolo);
        richiestaRuoloRepository.save(richiestaRuolo);
    }

    public List<UtenteAutenticato> viewChangeRoleRequests() {
        List<UtenteAutenticato> utenti = new ArrayList<>();
        this.richiestaRuoloRepository.findAll().forEach(richiestaRuolo -> utenti.add(this.utentiAutenticatiManager.getUtente(richiestaRuolo.getIdUtente())));
        return utenti;
    }

    public void rifiutaRichiesta(Long id) {
        this.richiestaRuoloRepository.findAll().forEach(richiestaRuolo -> {
            if (richiestaRuolo.getIdUtente().equals(id))
                this.richiestaRuoloRepository.delete(richiestaRuolo);
        });
    }

    public void approvaRichiesta(Long id) {
        UtenteAutenticato u = this.utentiAutenticatiManager.getUtente(id);
        switch (u.getRole()) {
            case CONTRIBUTOR -> u.setRole(Role.CONTRIBUTORAUTORIZZATO);
            case TURISTAAUTENTICATO -> u.setRole(Role.CONTRIBUTOR);
            default -> throw new IllegalArgumentException("Unexpected value: " + u.getRole());
        }
    }
}