package com.example.comuneids2024.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contest_generator")
    private Long id;

    private String name;

    private String descrizione;

    private boolean onInvite;

    private boolean isClosed;

    private List<UtenteAutenticato> utentiInvitati;

    private List<>
}