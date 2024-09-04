package com.example.comuneids2024.Model.DTO;

public class ContentDTO {
    private final String id;

    private final String nome;

    private final String descrizione;

    private final byte[] file;


    public ContentDTO(String id, String nome, String descrizione,byte[] file)
    {
        this.descrizione=descrizione;
        this.nome=nome;
        this.id=id;
        this.file=file;
    }

    public String getId() {
        return id;
    }
    public String getNome(){return nome;}

    public String getDescrizione(){return descrizione;}

}
