package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.DTO.ComuneDTO;
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
    private String comuneId;
    private Coordinate coordinate;
    private String nome;

    private List<POI> POIValidate;

    private List<POI> POIAttesa;

    private List<Itinerary> itinerarioValidato;

    private List<Itinerary> itinerarioAttesa;

    private UtenteAutenticato curatore;

    private List<Contest> contests;

    private List<POI> POISegnalati;

    private List<Itinerary> itinerariSegnalati;
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
    public String getComuneId() {
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
    public POI getPOI(String id)
    {
        if (id == null) {
            throw new IllegalArgumentException("L'ID non può essere null.");
        }

        return getPOIValidate().stream()
                .filter(p -> p.getPOIId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean isInComune(Coordinate coordinate) {
        for (POI poi : POIValidate) {
            if (poi.getCoordinate().equals(coordinate)) {
                return true; // Trovato un POI con le stesse coordinate
            }
        }
        return false; // Nessun POI trovato con le stesse coordinate
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
    public Itinerary getItinerary(String id)
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
    public void deletePOI(String id) {
        this.itinerarioValidato.stream().forEach(itinerary -> itinerary.getPOIs().removeIf(p -> p.getPOIId().equals(id)));
        this.itinerarioAttesa.stream().forEach(itinerary -> itinerary.getPOIs().removeIf(p -> p.getPOIId().equals(id)));
        this.POIValidate.removeIf(poi -> poi.getPOIId().equals(id));
    }

    /**
     * Elimina uno specifico itinerario.
     * @param id dell'itinerario da eliminare
     */
    public void deleteItinerario (String id){
        this.itinerarioValidato.removeIf(Itinerary -> Itinerary.getItineraryId().equals(id));
        this.itinerarioAttesa.removeIf(Itinerary -> Itinerary.getItineraryId().equals(id));
    }




    public Itinerary getItineraryPending(String id)
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
        this.POIValidate.add( poi);
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


    public void setCuratore(UtenteAutenticato curatore)
    {
        this.curatore=curatore;
    }


    public void setItinerarioValidato(List<Itinerary> itinerarioValidato) {
        this.itinerarioValidato = itinerarioValidato;
    }

    public void setItinerarioAttesa(List<Itinerary> itinerarioAttesa) {
        this.itinerarioAttesa = itinerarioAttesa;
    }

    public void setContests(List<Contest> contests) {
        this.contests = contests;
    }

    public void setPOIValidate(List<POI> POIValidate) {
        this.POIValidate = POIValidate;
    }

    public void setPOIAttesa(List<POI> POIAttesa) {
        this.POIAttesa = POIAttesa;
    }

    public boolean removeContest(String id) {
        return this.contests.removeIf(contest -> contest.getId().equals(id));
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void addPOISegnalato(POI p)
    {
        this.POISegnalati.add(p);
    }

    public void addItinerarioSegnalato(Itinerary i)
    {
        this.itinerariSegnalati.add(i);
    }

    public ComuneDTO getComune()
    {
        return new ComuneDTO(this.comuneId,this.nome,this.coordinate,this.curatore,this.POIValidate,this.POIAttesa,this.itinerarioValidato,this.itinerarioAttesa,this.contests);
    }


}
