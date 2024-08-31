package com.example.comuneids2024.Model.DTO;

import com.example.comuneids2024.Model.POI;
import com.example.comuneids2024.Model.UtenteAutenticato;

import java.util.ArrayList;
import java.util.List;

public class ItineraryDTO {

    private String id;
    private String name;
    private String descrizione;
    private List<POI> POIs;



    public ItineraryDTO(String id,String name, String descrizione, List<POI> POIs)
    {
        this.id=id;
        this.descrizione=descrizione;
        this.name=name;
        this.POIs=POIs;
    }

    public ItineraryDTO(String id, String nome, String descrizione)
    {
        this.id=id;
        this.name=nome;
        this.descrizione=descrizione;
        this.POIs=new ArrayList<>();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescrizione(String descrizione)
    {
        this.descrizione=descrizione;
    }
    public String getDescrizione()
    {
        return this.descrizione;
    }

    public String getNome(){
        return this.name;
    }

    public List<POI> getPOIs() {
        return POIs;
    }

    public void setPOIs(List<POI> POIs) {
        this.POIs = POIs;
    }
}
