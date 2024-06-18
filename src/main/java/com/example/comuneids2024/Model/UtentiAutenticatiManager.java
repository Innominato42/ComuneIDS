package com.example.comuneids2024.Model;

import com.example.comuneids2024.Repository.UtentiAutenticatiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UtentiAutenticatiManager {

    @Autowired
    private UtentiAutenticatiRepository utentiAutenticatoRepository;

    private List<UtenteAutenticato> utenti= new ArrayList<>();
    public List<UtenteAutenticato> getAllUtenti()
    {
        return utenti.stream().filter(u -> );
    }

    private boolean isContributor(Role role) {
        return role == Role.CONTRIBUTOR ||
                role == Role.CONTRIBUTORAUTORIZZATO ||
                role == Role.CURATORE;
    }

    public List<UtenteAutenticato> getAllContributors()
    {
            return this.utenti.stream()
                    .filter(u -> isContributor(u.getRole()))
                    .map(UtenteAutenticato::getUtenteInfo)
                    .collect(Collectors.toList());
    }

}
