package com.example.comuneids2024.Model.DTO;

import com.example.comuneids2024.Model.Content;
import com.example.comuneids2024.Model.UtenteAutenticato;

import java.util.List;

public class ContestDTO {


    private String id;

    private String name;

    private String descrizione;

    private boolean onInvite;

    private boolean isClosed;


    private List<UtenteAutenticato> utentiInvitati;


    private List<Content> contents;


    private UtenteAutenticato vincitore;

    public ContestDTO(String id, String name, String descrizione, boolean onInvite, boolean isClosed, List<UtenteAutenticato> utentiInvitati, List<Content> contents) {
        this.id = id;
        this.name = name;
        this.descrizione = descrizione;
        this.onInvite = onInvite;
        this.isClosed = isClosed;
        this.utentiInvitati = utentiInvitati;
        this.contents = contents;
        this.vincitore = null;
    }

    public String getId() {
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

    public UtenteAutenticato getVincitore() {
        return vincitore;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setOnInvite(boolean onInvite) {
        this.onInvite = onInvite;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public void setUtentiInvitati(List<UtenteAutenticato> utentiInvitati) {
        this.utentiInvitati = utentiInvitati;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public void setVincitore(UtenteAutenticato vincitore) {
        this.vincitore = vincitore;
    }
}
