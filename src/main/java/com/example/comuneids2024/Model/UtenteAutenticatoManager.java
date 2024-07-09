package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.DTO.UtenteAutenticatoDTO;
import com.example.comuneids2024.Repository.UtenteAutenticatoRepository;
import jakarta.persistence.OneToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
@Component
public class UtenteAutenticatoManager {

    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;


    private List<UtenteAutenticato> registrazioniUtenti = new ArrayList<>();

    public List<UtenteAutenticatoDTO> getAllContributors()
    {
        return utenteAutenticatoRepository.findAll().stream().filter(u -> u.getRole().equals(Role.CONTRIBUTOR) ||
                u.getRole().equals(Role.CONTRIBUTORAUTORIZZATO)).map(UtenteAutenticato::getUtenteInfo).toList();
    }

    public void addUtente(UtenteAutenticato utenteAutenticato)
    {
        this.utenteAutenticatoRepository.save(utenteAutenticato);
    }

    public UtenteAutenticato getUtente(String id)
    {
        return this.utenteAutenticatoRepository.findById(id).get();
    }

    public UtenteAutenticatoDTO getUtenteDTO(String id) {
        return this.utenteAutenticatoRepository.findById(id).get().getUtenteInfo();
    }

    /**
     * Controlla se esiste un utente con l email e la password passati come parametri
     * @param email email dell utente
     * @param username username dell utente
     * @return true se esiste, false altrimenti
     */
    public boolean containsUtente(String email, String username)
    {
        return utenteAutenticatoRepository.findAll().stream().anyMatch(u -> u.getEmail().equals(email) || (u.getUsername().equals(username)));
    }

    public List<UtenteAutenticato> getAllUtenti (){
        return utenteAutenticatoRepository.findAll();
    }

    public void addRegistrationUser(UtenteAutenticato user) {
        this.registrazioniUtenti.add(user);
        this.utenteAutenticatoRepository.save(user);
    }

    public void approvaRegistration(String id) {
        this.utenteAutenticatoRepository.findAll().add(this.registrazioniUtenti.stream().filter(u -> u.getId().equals(id)).findFirst().get());
        this.registrazioniUtenti.removeIf(u -> u.getId().equals(id));
    }

    public void rifiutaRegistration(String id) {
        this.registrazioniUtenti.removeIf(u -> u.getId().equals(id));
        this.utenteAutenticatoRepository.deleteById(id);
    }

    public void cambiaRuolo(String id, Role role) {
        UtenteAutenticato utente = utenteAutenticatoRepository.findById(id).get();
        utente.setRole(role);
        this.utenteAutenticatoRepository.save(utente);
    }

    public List<UtenteAutenticatoDTO> viewAllUser()
    {
        return this.utenteAutenticatoRepository.findAll().stream().filter(u -> !u.getRole().equals(Role.GESTORE)).map(UtenteAutenticato::getUtenteInfo).toList();
    }

    public List<UtenteAutenticatoDTO> viewRegistrationUsers() {
        return this.registrazioniUtenti.stream().map(UtenteAutenticato::getUtenteInfo).toList();
    }



}