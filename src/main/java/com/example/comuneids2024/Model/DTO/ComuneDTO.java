package com.example.comuneids2024.Model.DTO;

import com.example.comuneids2024.Model.*;

import java.util.List;

public class ComuneDTO {


    private String id;
    private String nome;
    private Coordinate coordinate;
    private UtenteAutenticato curatore;
    private List<POI> POIValidate;
    private List<POI> POIAttesa;
    private List<Itinerary> itinerarioValidato;
    private List<Itinerary> itinerarioAttesa;
    private List<Contest> contests;

    public ComuneDTO(String id, String nome, Coordinate coordinate, UtenteAutenticato curatore, List<POI> POIValidate, List<POI> POIAttesa, List<Itinerary> itinerarioValidato, List<Itinerary> itinerarioAttesa, List<Contest> contests) {
        this.id = id;
        this.nome = nome;
        this.coordinate = coordinate;
        this.curatore = curatore;
        this.POIValidate = POIValidate;
        this.POIAttesa = POIAttesa;
        this.itinerarioValidato = itinerarioValidato;
        this.itinerarioAttesa = itinerarioAttesa;
        this.contests = contests;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public UtenteAutenticato getCuratore() {
        return curatore;
    }

    public void setCuratore(UtenteAutenticato curatore) {
        this.curatore = curatore;
    }

    //Ritorna la lista dei POI validati
    public List<POI> getPOIValidate() {
        return POIValidate;
    }

    public void setPOIValidate(List<POI> POIValidate) {
        this.POIValidate = POIValidate;
    }

    //Ritorna la lista dei POI in attesa
    public List<POI> getPOIAttesa() {
        return POIAttesa;
    }

    public void setPOIAttesa(List<POI> POIAttesa) {
        this.POIAttesa = POIAttesa;
    }

    public List<Itinerary> getItinerarioValidato() {
        return itinerarioValidato;
    }

    public void setItinerarioValidato(List<Itinerary> itinerarioValidato) {
        this.itinerarioValidato = itinerarioValidato;
    }

    public List<Itinerary> getItinerarioAttesa() {
        return itinerarioAttesa;
    }

    public void setItinerarioAttesa(List<Itinerary> itinerarioAttesa) {
        this.itinerarioAttesa = itinerarioAttesa;
    }

    public List<Contest> getContests() {
        return contests;
    }

    public void setContests(List<Contest> contests) {
        this.contests = contests;
    }
}
