package com.example.comuneids2024.Model;

import com.example.comuneids2024.Repository.ItineraryRepository;
import com.example.comuneids2024.Repository.POIRepositoriy;
import com.example.comuneids2024.Repository.PreferitiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PreferitiManager {

    @Autowired
    private POIRepositoriy poiRepositoriy;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private PreferitiRepository preferitiRepository;

    @Autowired
    private UtenteAutenticatoManager utenteAutenticatoManager;


    public void addPOItoFavourites(Long id, Long idComune, Long idPOI)
    {

    }


}
