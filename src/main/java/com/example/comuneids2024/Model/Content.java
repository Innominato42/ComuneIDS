package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.GI.ContentGI;
import jakarta.persistence.*;

@Entity
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_generator")
    private Long idContent;

    private String nome;

    private String descrizione;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UtenteAutenticato creatore;
    private byte[] file;

    public Content(String nome,String descrizione, byte[] file)
    {
        this.nome=nome;
        this.descrizione=descrizione;
        this.file=file;
    }

    public Content() {

    }


    public void addFile(byte[] file)
    {
        this.file=file;
    }


    public UtenteAutenticato getCreatore()
    {
        return this.creatore;
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
    public Content getContentInfo()
    {
        return  new Content(this.getNome(),this.getDescrizione(),this.getFile());
    }

}
