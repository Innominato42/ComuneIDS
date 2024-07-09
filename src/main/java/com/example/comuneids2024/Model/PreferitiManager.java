package com.example.comuneids2024.Model;

import com.example.comuneids2024.Repository.ItineraryRepository;
import com.example.comuneids2024.Repository.POIRepositoriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PreferitiManager {

    @Autowired
    private POIRepositoriy poiRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private UtenteAutenticatoManager utenteAutenticatoManager;


    public boolean addPOItoFavourites(String id, String idComune, String idPOI) {
        UtenteAutenticato utente = utenteAutenticatoManager.getUtente(id);
        if (utente != null) {
            POI poi = poiRepository.findById(idPOI).orElse(null);
            if (poi != null) {
                utente.addPOIToFavourites(poi);
                utenteAutenticatoManager.addUtente(utente);
                return true;
            } else {
                throw new RuntimeException("POI non trovato");
            }
        } else {
            throw new RuntimeException("Utente non trovato");
        }

    }

    public void removePOIfromFavourites(String id, String idPOI) {
        UtenteAutenticato utente = utenteAutenticatoManager.getUtente(id);
        if (utente != null) {
            POI poi = poiRepository.findById(idPOI).orElse(null);
            if (poi != null) {
                utente.removePOIFromFavourites(poi);
                utenteAutenticatoManager.addUtente(utente);
            } else {
                throw new RuntimeException("POI non trovato");
            }
        } else {
            throw new RuntimeException("Utente non trovato");
        }
    }

    public void addItineraryToFavourites(String id, String idComune, String idItinerary) {
        UtenteAutenticato utente = utenteAutenticatoManager.getUtente(id);
        if (utente != null) {
            Itinerary itinerary = itineraryRepository.findById(idItinerary).orElse(null);
            if (itinerary != null) {
                utente.addItineraryToFavourites(itinerary);
                utenteAutenticatoManager.addUtente(utente);
            } else {
                throw new RuntimeException("Itinerario non trovato");
            }
        } else {
            throw new RuntimeException("Utente non trovato");
        }
    }

    public void removeItineraryFromFavourites(String id, String idItinerary) {
        UtenteAutenticato utente = utenteAutenticatoManager.getUtente(id);
        if (utente != null) {
            Itinerary itinerary = itineraryRepository.findById(idItinerary).orElse(null);
            if (itinerary != null) {
                utente.removeItineraryFromFavourites(itinerary);
                utenteAutenticatoManager.addUtente(utente);
            } else {
                throw new RuntimeException("Itinerario non trovato");
            }
        } else {
            throw new RuntimeException("Utente non trovato");
        }
    }


}
