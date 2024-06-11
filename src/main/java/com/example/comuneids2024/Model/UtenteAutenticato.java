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
    private String Username;
    private String password;
    private Role ruolo;
    private String email;
    @Override
    public Role getRole() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
