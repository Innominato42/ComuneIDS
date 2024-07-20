package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.Comune;
import com.example.comuneids2024.Model.Itinerary;
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
        Comune c =this.comuneRepository.findById(idComune).get();
        c.addItinerary(i);
        this.comuneRepository.save(c);
    }

    public void createItineraryPending(String idComune, Itinerary i, String[] poi)
    {
        for(String p : poi )
        {
            i.addPOI(this.poiRepository.findById(p).orElse(null));
        }
        Comune c =this.comuneRepository.findById(idComune).get();
        c.addItineraryPending(i);
        this.comuneRepository.save(c);
    }

    public Itinerary viewItinerary(String ItineraryID)
    {
        return this.itineraryRepository.findById(ItineraryID).orElse(null);
    }

}
