package com.example.comuneids2024.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itinerary_id_seq")
    private Long ItineraryId;

    private String nome;

    private String descrizione;


    @ManyToMany
    private List<POI> POIs;

    private Itinerary(String nome,String descrizione)
    {
        this.nome=nome;
        this.descrizione=descrizione;
        this.POIs=new ArrayList<>();
    }



    public Itinerary() {
        this.POIs=new ArrayList<>();
    }

    public String getNome()
    {
        return nome;
    }

    public String getDescrizione()
    {
        return descrizione;
    }

    public List<POI> getPOIs()
    {
        return POIs;
    }
    public Long getItineraryId()
    {
        return ItineraryId;
    }

    public void addPOI(POI p)
    {
        if(p==null)
        {
            throw new NullPointerException();
        }
        this.POIs.add(p);
    }
}










