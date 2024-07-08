package com.example.comuneids2024.Model.DTO;

import com.example.comuneids2024.Model.Itinerary;
import com.example.comuneids2024.Model.POI;
import com.example.comuneids2024.Model.Role;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

public class UtenteAutenticatoDTO {

    private Long id;
    private String username;
    private String password;
    private Role ruolo;
    private String email;

    private List<POI> POIFavourites;

    private List<Itinerary> ItineraryFavourites;

    public UtenteAutenticatoDTO(Long id, String username, String password, Role ruolo, String email, List<POI> POIFavourites, List<Itinerary> itineraryFavourites) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
        this.email = email;
        this.POIFavourites = POIFavourites;
        ItineraryFavourites = itineraryFavourites;
    }

    public UtenteAutenticatoDTO()
    {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRuolo() {
        return ruolo;
    }

    public void setRuolo(Role ruolo) {
        this.ruolo = ruolo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setItineraryFavourites(List<Itinerary> itineraryFavourites) {
        ItineraryFavourites = itineraryFavourites;
    }
}
