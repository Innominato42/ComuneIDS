package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.Comune;
import com.example.comuneids2024.Model.Itinerary;
import com.example.comuneids2024.Model.POI;
import com.example.comuneids2024.Repository.ComuneRepository;
import com.example.comuneids2024.Repository.ItineraryRepository;
import com.example.comuneids2024.Repository.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItineraryController {

    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private POIRepository poiRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    public void createItinerary(String idComune, Itinerary i, String[] poi)
    {
        for(String p : poi )
        {
            i.addPOI(this.poiRepository.findById(p).orElse(null));
        }
        Comune c =this.comuneRepository.findById(idComune).orElse(null);
        if(c==null)
        {
            throw new NullPointerException("Comune non trovato");
        }
        for (String p: poi)
        {
            POI p1= poiRepository.findById(p).orElse(null);
            if(!c.isInComune(p1))
            {
                throw new IllegalArgumentException("POI non presente nel comune");
            }
        }

        c.addItinerary(i);
        itineraryRepository.save(i);
        this.comuneRepository.save(c);
    }

    public void createItineraryPending(String idComune, Itinerary i, String[] poi)
    {
        for(String p : poi )
        {
            i.addPOI(this.poiRepository.findById(p).orElse(null));
        }
        Comune c =this.comuneRepository.findById(idComune).orElse(null);
        if(c==null)
        {
            throw new NullPointerException("Comune non trovato");
        }
        for (String p: poi)
        {
            POI p1= poiRepository.findById(p).orElse(null);
            if(!c.isInComune(p1))
            {
                throw new IllegalArgumentException("POI non presente nel comune");
            }
        }

        c.addItineraryPending(i);
        itineraryRepository.save(i);
        this.comuneRepository.save(c);
    }

    public Itinerary viewItinerary(String ItineraryID)
    {
        return this.itineraryRepository.findById(ItineraryID).orElse(null);
    }

}
