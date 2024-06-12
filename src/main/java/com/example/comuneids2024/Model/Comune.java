package com.example.comuneids2024.Model;

import jakarta.persistence.*;

import javax.imageio.stream.IIOByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comune {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comune_generator")
    private Long comuneId;
    private Coordinate coordinate;
    private String nome;
    @OneToMany
    private List<POI> POIValidate;

    @OneToMany
    private List<POI> POIAttesa;

    @OneToMany
    private List<Itinerary> itinerarioValidato;

    @OneToMany
    private List<Itinerary> itinerarioAttesa;

    @OneToOne
    private UtenteAutenticato curatore;

    public Comune(String nome,Coordinate coordinate, UtenteAutenticato curatore){
        if(curatore.getRole().equals(Role.CURATORE))
        {
            this.curatore=curatore;
        }
        else {
            throw new IllegalArgumentException("L' utente non è un curatore");
        }
        this.nome=nome;
        this.coordinate=coordinate;
        POIAttesa =new ArrayList<>();
        POIValidate=new ArrayList<>();
        itinerarioAttesa=new ArrayList<>();
        itinerarioValidato=new ArrayList<>();
    }

    public Long getComuneId() {
        return comuneId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public String getNome() {
        return nome;
    }

    public List<POI> getPOIValidate() {
        return POIValidate;
    }

    public List<POI> getPOIAttesa() {
        return POIAttesa;
    }

    public List<Itinerary> getItinerarioValidato() {
        return itinerarioValidato;
    }

    public List<Itinerary> getItinerarioAttesa() {
        return itinerarioAttesa;
    }

    public UtenteAutenticato getCuratore() {
        return curatore;
    }

    /**
     * Controlla se esiste un POI con le coordinate passate come parametro
     * @param coordinate le coordinate da controllare
     * @return true se il POI &grave; presente, false altrimenti
     */
    public boolean isTherePOI(Coordinate coordinate)
    {
        for(POI p: this.POIValidate)
        {
            if(p.getCoord().equals(coordinate))
            {
                return true;
            }
        }
        for(POI p: this.POIAttesa)
        {
            if(p.getCoord().equals(coordinate))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Restituisce il POI con l'id passato come parametro
     * @param id di cui cercare il POI
     * @return il POI se presente, null altrimenti
     */
    public POI getPOI(Long id)
    {
        for(POI p : getPOIValidate())
        {
            if(p.getPOIId().equals(id))
            {
                return p;
            }
        }
        return null;
    }

    public void addItinerary(Itinerary itinerary)
    {
        this.itinerarioValidato.add(itinerary);
    }

    public void addItineraryPending(Itinerary itinerary)
    {
        this.itinerarioAttesa.add(itinerary);
    }



}
