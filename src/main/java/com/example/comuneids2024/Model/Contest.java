package com.example.comuneids2024.Model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Optional;

@Document(collection = "contest")
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contest_generator")
    private Long id;

    private String name;

    private String descrizione;

    private boolean onInvite;

    private boolean isClosed;

    @DBRef
    private List<UtenteAutenticato> utentiInvitati;

    @DBRef
    private List<Content> contents;

    @DBRef
    private UtenteAutenticato vincitore;

    public Contest(String nome, String descrizione, boolean onInvite, boolean isClosed, List<UtenteAutenticato> utentiInvitati, List<Content> content)
    {
        this.contents=content;
        this.utentiInvitati = utentiInvitati;
        this.name = nome;
        this.descrizione = descrizione;
    }

    public Contest() {

    }

    public void setVincitore(UtenteAutenticato vincitore)
    {
        this.vincitore=vincitore;
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

    public void setNome(String nome)
    {
        this.name=nome;
    }

    public void setDescrizione(String descrizione)
    {
        this.descrizione=descrizione;
    }

    public void setContents(List<Content> contents)
    {
        this.contents=contents;
    }

    public void addContestInfo(String nome, String descrizione, boolean onInvite, List<Content> content)
    {
        setContents(content);
        setNome(nome);
        setDescrizione(descrizione);
        setOnInvite(onInvite);
    }
    /**
     * Controlla se un contributor partecipa al contest
     * @param contributorId id del contributor da controllare
     * @return true se il contributor è invitato, false altrimenti
     * @return true se il contest non è su invito
     */
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

    public void closeContest()
    {
        this.isClosed=true;
    }

    public Contest getContestInfo()
    {
        return new Contest(this.name,this.descrizione,this.onInvite, this.isClosed,this.utentiInvitati,this.contents);
    }

    /**
     * Restituisce l email del creatore del contest
     * @param contentId l id del content di cui trovare l autore
     * @return l email dell autore
     */

    public String getAutoreContentEmail(Long contentId) {
        Optional<Content> contentOpt = contents.stream()
                .filter(content -> content.getId().equals(contentId))
                .findFirst();

        if (contentOpt.isPresent()) {
            UtenteAutenticato author = contentOpt.get().getCreatore();
            return author != null ? author.getEmail() : null;
        } else {
            return null;
        }
    }


    public void inviteContributor(UtenteAutenticato user) {
        this.utentiInvitati.add(user);
    }
}