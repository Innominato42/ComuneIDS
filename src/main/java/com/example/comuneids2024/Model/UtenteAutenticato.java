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

    public UtenteAutenticato(Long id, String username, String password, Role ruolo, String email)
    {
        this.email=email;
        this.id=id;
        this.username=username;
        this.password=password;
        this.ruolo=ruolo;
    }


    public UtenteAutenticato() {

    }

    public Long getId()
    {
        return id;
    }

    public String getEmail() {return this.email;}

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

    @Override
    public UtenteAutenticato getUtenteInfo()
    {
        return new UtenteAutenticato(this.id,this.username,this.password,this.ruolo,this.email);
    }

    public void setRole(Role r)
    {
        this.ruolo=r;
    }


}
