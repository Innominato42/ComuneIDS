package com.example.comuneids2024.Model;

import com.example.comuneids2024.Repository.ComuneRepository;
import com.example.comuneids2024.Repository.ItineraryRepository;
import com.example.comuneids2024.Repository.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PreferitiManager {

    @Autowired
    private POIRepository poiRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private UtenteAutenticatoManager utenteAutenticatoManager;

    @Autowired
    private ComuneRepository comuneRepository;


    public boolean addPOItoFavourites(String id, String idComune, String idPOI) {
        UtenteAutenticato utente = utenteAutenticatoManager.getUtente(id);
        if (utente != null) {
            POI poi = poiRepository.findById(idPOI).orElse(null);
            if (poi ==null) {
                throw new RuntimeException("POI non trovato");
            } else {

                utente.addPOIToFavourites(poi);
                utenteAutenticatoManager.addUtente(utente);
                return true;
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

    public boolean addItineraryToFavourites(String id, String idComune, String idItinerary) {
        UtenteAutenticato utente = utenteAutenticatoManager.getUtente(id);
        Comune comune = comuneRepository.findById(idComune).orElse(null);
        if(comune==null)
        {
            throw new NullPointerException("Comune non trovato");
        }
        if (utente != null) {
            Itinerary itinerary=comune.getItinerary(idItinerary);
            if (itinerary != null) {
                utente.addItineraryToFavourites(itinerary);
                utenteAutenticatoManager.addUtente(utente);
                return true;
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
