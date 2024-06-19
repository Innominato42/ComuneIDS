package com.example.comuneids2024.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contest_generator")
    private Long id;

    private String name;

    private String descrizione;

    private boolean onInvite;

    private boolean isClosed;

    @ManyToMany
    private List<UtenteAutenticato> utentiInvitati;

    @ManyToMany
    private List<Content> contents;

    public Contest(String nome, String descrizione, boolean onInvite, boolean isClosed, List<UtenteAutenticato> utentiInvitati, List<Content> content)
    {
        this.contents=content;
        this.utentiInvitati = utentiInvitati;
        this.name = nome;
        this.descrizione = descrizione;
    }

    public Contest() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public boolean isOnInvite() {
        return onInvite;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public List<UtenteAutenticato> getUtentiInvitati() {
        return utentiInvitati;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setOnInvite(boolean b)
    {
        this.onInvite=b;
    }

    public void addContent (Content content){
        this.contents.add(content);
    }

    public boolean contributorInvited(Long contributorId) {

        if(isOnInvite())
        {
            return utentiInvitati.stream().anyMatch(utenteAutenticato -> utenteAutenticato.getId()== contributorId);

        }
        else
        {
            return true;
        }

    }

    public Contest getContestInfo()
    {
        return new Contest(this.name,this.descrizione,this.onInvite, this.isClosed,this.utentiInvitati,this.contents);
    }
}