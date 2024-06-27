package com.example.comuneids2024.Model.DTO;

import com.example.comuneids2024.Model.POI;
import com.example.comuneids2024.Model.UtenteAutenticato;

import java.util.List;

public class ItineraryDTO {

    private String name;
    private UtenteAutenticato curatore;
    private List<POI> POIs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UtenteAutenticato getCuratore() {
        return curatore;
    }

    public void setCuratore(UtenteAutenticato curatore) {
        this.curatore = curatore;
    }

    public List<POI> getPOIs() {
        return POIs;
    }

    public void setPOIs(List<POI> POIs) {
        this.POIs = POIs;
    }
}
