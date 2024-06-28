package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.DTO.UtenteAutenticatoDTO;

public interface Utente {

    public Role getRole();
    public String getUsername();
    public String getPassword();

    UtenteAutenticatoDTO getUtenteInfo();
}
