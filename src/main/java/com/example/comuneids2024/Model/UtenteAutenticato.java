package com.example.comuneids2024.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UtenteAutenticato implements Utente{


    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utente_generator")
    @Id
    private Long id;
    private String username;
    private String password;
    private Role ruolo;
    private String email;

    public UtenteAutenticato(String username,String password, Role ruolo, String email)
    {
        this.email=email;
        this.password=password;
        this.ruolo=ruolo;
        this.username=username;
    }

    public Long getId()
    {
        return id;
    }

    @Override
    public Role getRole() {
        return this.ruolo;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }





}
