package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.DTO.UtenteAutenticatoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "utentiAutenticati")
public class UtenteAutenticato implements Utente{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utente_generator")

    private Long id;
    private String username;
    private String password;
    private Role ruolo;
    private String email;

    @DBRef
    private List<POI> POIFavourites=new ArrayList<>();

    @DBRef
    private List<Itinerary> ItineraryFavourites=new ArrayList<>();

    public UtenteAutenticato(String username,String password, Role ruolo, String email)
    {
        this.email=email;
        this.password=password;
        this.ruolo=ruolo;
        this.username=username;
    }

    public UtenteAutenticato(Long id, String username, String password, Role ruolo, String email)
    {
        this.email=email;
        this.id=id;
        this.username=username;
        this.password=password;
        this.ruolo=ruolo;
    }


    public UtenteAutenticato() {

    }

    public Long getId()
    {
        return id;
    }

    public String getEmail() {return this.email;}

    @Override
    public Role getRole() {
        return this.ruolo;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public UtenteAutenticatoDTO getUtenteInfo()
    {
        return new UtenteAutenticatoDTO(this.id,this.username,this.password,this.ruolo,this.email,this.getPOIFavourites(),this.getItineraryFavourites());
    }

    public void setRole(Role r)
    {
        this.ruolo=r;
    }

    public List<POI> getPOIFavourites() {
        return POIFavourites;
    }

    public void setPOIFavourites(List<POI> POIFavourites) {
        this.POIFavourites = POIFavourites;
    }

    public List<Itinerary> getItineraryFavourites() {
        return ItineraryFavourites;
    }

    public void setItineraryFavourites(List<Itinerary> ItineraryFavourites) {
        this.ItineraryFavourites = ItineraryFavourites;
    }

    public void addPOIToFavourites(POI poi) {
        if (!POIFavourites.contains(poi)) {
            POIFavourites.add(poi);
        }
    }

    // Rimuovi un POI dai preferiti
    public void removePOIFromFavourites(POI poi) {
        POIFavourites.remove(poi);
    }

    // Aggiungi un Itinerary ai preferiti
    public void addItineraryToFavourites(Itinerary itinerary) {
        if (!ItineraryFavourites.contains(itinerary)) {
            ItineraryFavourites.add(itinerary);
        }
    }

    // Rimuovi un Itinerary dai preferiti
    public void removeItineraryFromFavourites(Itinerary itinerary) {
        ItineraryFavourites.remove(itinerary);
    }

}
