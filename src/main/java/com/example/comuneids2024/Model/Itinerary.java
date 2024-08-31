package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.DTO.ItineraryDTO;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Document(collection = "Itinerary")
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itinerary_id_seq")
    private String itineraryId;

    private String nome;

    private String descrizione;

    private List<POI> POIs;

    public Itinerary(String nome,String descrizione)
    {
        this.nome=nome;
        this.descrizione=descrizione;
        this.POIs=new ArrayList<>();
    }

    public Itinerary(String nome)
    {
        this.nome=nome;
        this.descrizione=null;
        this.POIs=new ArrayList<>();
    }



    public Itinerary() {
        this.POIs=new ArrayList<>();
    }

    public void removePOI()
    {
        this.POIs= new ArrayList<>();
    }
    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        if(nome == null)
        {
            return;
        }
        this.nome=nome;
    }

    public void setDescrizione(String descrizione)
    {
        if(descrizione == null)
        {
            return;
        }
        this.descrizione=descrizione;
    }

    public String getDescrizione()
    {
        return descrizione;
    }

    public List<POI> getPOIs()
    {
        return POIs;
    }
    public String getItineraryId()
    {
        return this.itineraryId;
    }
    public void addPOIS(List<POI> pois)
    {
        this.POIs.addAll(pois);
    }
    public void addPOI(POI p)
    {
        if(p==null)
        {
            throw new NullPointerException();
        }
        this.POIs.add(p);
    }

    public ItineraryDTO getItineraryInfo()
    {

        return new ItineraryDTO(this.itineraryId,this.nome,this.descrizione,this.getPOIs());
    }

    public void addItineraryInfo(String nome, String descrizione)
    {
        this.setNome(nome);
        this.setDescrizione(descrizione);
    }
}










