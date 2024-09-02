package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.*;
import com.example.comuneids2024.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentController {

    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private POIRepository poiRepository;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;


    public void insertContentToPOI(String idComune, String idPOI, Content c)
    {
        Optional<Comune> optionalComune = this.comuneRepository.findById(idComune);
        if (optionalComune.isPresent()) {
            Comune comune = optionalComune.get();
            POI poi=comune.getPOI(idPOI);
            if(poi==null)
            {
                throw new NullPointerException("POI non trovato");
            }
            List<Itinerary> itinerary=comune.getAllItinerary();
            int cont=0;
            for(Itinerary i: itinerary)
            {
                if(i.getPOIs().get(cont).getPOIId().equals(poi.getPOIId()))
                {
                    comune.getPOI(idPOI).addContent(c);
                    i.getPOIs().get(cont).addContent(c);
                    this.itineraryRepository.save(i);
                    this.contentRepository.save(c);
                    this.poiRepository.save(poi);
                    this.comuneRepository.save(comune);
                    return;
                }
                cont++;
            }
            comune.getPOI(idPOI).addContent(c);
            //poi.addContent(c);
            this.contentRepository.save(c);
            this.poiRepository.save(poi);
            this.comuneRepository.save(comune);
        } else {
            throw new RuntimeException("Comune with ID " + idComune + " not found.");
        }
    }

    public void insertContentToPOIPending(String idComune, String idPOI, Content c)
    {
        Optional<Comune> optionalComune = this.comuneRepository.findById(idComune);
        if (optionalComune.isPresent()) {
            Comune comune = optionalComune.get();
            POI poi=comune.getPOI(idPOI);
            if(poi==null)
            {
                throw new NullPointerException("POI non trovato");
            }
            List<Itinerary> itinerary=comune.getAllItinerary();
            int cont=0;
            for(Itinerary i: itinerary)
            {
                if(i.getPOIs().get(cont).getPOIId().equals(poi.getPOIId()))
                {
                    comune.getPOI(idPOI).addContentPending(c);
                    i.getPOIs().get(cont).addContentPending(c);
                    this.itineraryRepository.save(i);
                    this.contentRepository.save(c);
                    this.poiRepository.save(poi);
                    this.comuneRepository.save(comune);
                    return;
                }
                cont++;
            }
            comune.getPOI(idPOI).addContentPending(c);
            this.contentRepository.save(c);
            this.poiRepository.save(poi);
            this.comuneRepository.save(comune);
        } else {
            throw new RuntimeException("Comune with ID " + idComune + " not found.");
        }
    }

    public void insertContentToContest(String idComune, String idContest, Content c)
    {
        Optional<Comune> optionalComune=this.comuneRepository.findById(idComune);
        Optional<Contest> optionalContest=this.contestRepository.findById(idContest);
        if(optionalComune.isPresent())
        {
            Comune comune= optionalComune.get();
            comune.getContest(idContest).addContent(c);
            this.comuneRepository.save(comune);
            this.contentRepository.save(c);
        }
        else {
            throw new RuntimeException("Comune with ID " + idComune + " not found.");
        }
        if(optionalContest.isPresent())
        {
            Contest contest=optionalContest.get();

            contest.addContent(c);
            this.contestRepository.save(contest);
        }
        else {
            throw new RuntimeException("Contest con ID " + idContest + " not found.");
        }
    }

    public Content viewContent(String contentID) {
        return contentRepository.findById(contentID).orElse(null);
    }

}
