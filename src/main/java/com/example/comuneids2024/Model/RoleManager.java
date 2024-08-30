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

    public void nuovoRuolo(String utenteId, Role role) {
        Optional<UtenteAutenticato> utenteOpt = utenteAutenticatoRepository.findById(utenteId);

        if (utenteOpt.isPresent()) {
            UtenteAutenticato utente = utenteOpt.get();
            utente.setRole(role);
            utenteAutenticatoRepository.save(utente);
        } else {
            throw new RuntimeException("Utente non trovato");
        }
    }

    public void nuovaRichiestaCambioRuolo(String utenteId, Role ruolo) {
        RichiestaRuolo richiestaRuolo = new RichiestaRuolo(utenteId, ruolo);
        richiestaRuoloRepository.save(richiestaRuolo);
    }

    public List<RichiestaRuolo> viewChangeRoleRequests() {
        /*List<UtenteAutenticato> utenti = new ArrayList<>();
        this.richiestaRuoloRepository.findAll().forEach(richiestaRuolo -> utenti.add(this.utentiAutenticatiManager.getUtente(richiestaRuolo.getIdUtente())));
        return utenti;*/
        List<RichiestaRuolo> richieste =richiestaRuoloRepository.findAll();
        return richieste;

    }

    public void rifiutaRichiesta(String id) {
        RichiestaRuolo richiestaRuolo= richiestaRuoloRepository.findById(id).orElse(null);
        if(richiestaRuolo==null)
        {
            throw new NullPointerException("Richiesta non trovata");
        }
        richiestaRuoloRepository.delete(richiestaRuolo);
    }

    public void approvaRichiesta(String id)
    {
        RichiestaRuolo richiestaRuolo= this.richiestaRuoloRepository.findById(id).orElse(null);
        if(richiestaRuolo==null)
        {
            throw new NullPointerException("richiesta non trovata");
        }
        UtenteAutenticato utente= this.utenteAutenticatoRepository.findById(richiestaRuolo.getIdUtente()).orElse(null);
        if(utente==null)
        {
            throw new NullPointerException("Utente non trovato");
        }
        if(richiestaRuolo.getRuoloRichiesto().equals(Role.GESTORE))
        {
            throw new RuntimeException("Impossibile diventare il gestore della piattaforma");
        }
        else {
            utente.setRole(richiestaRuolo.getRuoloRichiesto());
        }
        this.richiestaRuoloRepository.delete(richiestaRuolo);
        this.utenteAutenticatoRepository.save(utente);
    }
}