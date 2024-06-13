package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.GI.ContentGI;
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

    private byte[] file;

    public Content(String nome,String descrizione, byte[] file)
    {
        this.nome=nome;
        this.descrizione=descrizione;
        this.file=file;
    }

    public Content() {

    }

    public Long getId(){return this.idContent;}
    public String getNome()
    {
        return this.nome;
    }

    public String getDescrizione()
    {
        return this.descrizione;
    }

    public byte[] getFile(){return this.file;}
    public ContentGI getContentInfo()
    {
        return new ContentGI(this.getId(),this.getNome(),this.getDescrizione(),this.getFile());
    }

}
