package com.example.comuneids2024.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_generator")
    private Long idContent;

    private String nome;

    private String descrizione;

    public Content(String nome,String descrizione)
    {
        this.nome=nome;
        this.descrizione=descrizione;
    }

    public Content() {

    }

    public String getNome()
    {
        return this.nome;
    }

    public String getDescrizione()
    {
        return this.descrizione;
    }

}
