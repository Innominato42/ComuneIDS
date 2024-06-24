package com.example.comuneids2024.Model;

public interface Utente {

    public Role getRole();
    public String getUsername();
    public String getPassword();

    UtenteAutenticato getUtenteInfo();
}
