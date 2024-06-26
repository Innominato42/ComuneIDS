package com.example.comuneids2024.Model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.imageio.stream.IIOByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "comuni")
public class Comune {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comune_generator")
    private Long comuneId;
    private Coordinate coordinate;
    private String nome;
    @DBRef
    private List<POI> POIValidate;

    @DBRef
    private List<POI> POIAttesa;

    @DBRef
    private List<Itinerary> itinerarioValidato;

    @DBRef
    private List<Itinerary> itinerarioAttesa;

    @DBRef
    private UtenteAutenticato curatore;

    @DBRef
    private List<Contest> contests;
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
        this.contests=new ArrayList<>();
    }

    public Comune() {

    }

    public void addContest(Contest contest)
    {
        this.contests.add(contest);
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


    /**
     * Resistuisce l'itinerario con l'id passato come argomento
     * @param id dell itinerario
     * @return l itinerario con quell id, null altrimenti
     */
    public Itinerary getItinerary(Long id)
    {
        for(Itinerary i : itinerarioValidato)
        {
            if(i.getItineraryId()==id)
            {
                return i;
            }
        }
        return null;
    }

    /**
     * Elimina uno specifico POI. Scorro con una stream tutte le liste che possono contenere POI
     * @param id del POI da eliminare
     */
    public void deletePOI(Long id) {
        this.itinerarioValidato.stream().forEach(itinerary -> itinerary.getPOIs().removeIf(p -> p.getPOIId().equals(id)));
        this.itinerarioAttesa.stream().forEach(itinerary -> itinerary.getPOIs().removeIf(p -> p.getPOIId().equals(id)));
        this.POIValidate.removeIf(poi -> poi.getPOIId().equals(id));
    }

    /**
     * Elimina uno specifico itinerario.
     * @param id dell'itinerario da eliminare
     */
    public void deleteItinerario (Long id){
        this.itinerarioValidato.removeIf(Itinerary -> Itinerary.getItineraryId().equals(id));
        this.itinerarioAttesa.removeIf(Itinerary -> Itinerary.getItineraryId().equals(id));
    }




    public Itinerary getItineraryPending(Long id)
    {
        for(Itinerary i : itinerarioAttesa)
        {
            if(i.getItineraryId()==id)
            {
                return i;
            }
        }
        return null;
    }

    public void addPOI(POI poi)
    {
        this.POIValidate.add(poi);
    }
    public void addPOIPending(POI poi)
    {
        this.POIAttesa.add(poi);
    }

    public List<POI> getAllPOI()
    {
        return POIValidate;
    }
    public List<POI> getAllPOIPending()
    {
        return POIAttesa;
    }


    public boolean removeContest(Long id) {
        return this.contests.removeIf(contest -> contest.getId().equals(id));
    }

}
