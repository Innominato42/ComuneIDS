package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.Comune;
import com.example.comuneids2024.Model.Content;
import com.example.comuneids2024.Model.POI;
import com.example.comuneids2024.Repository.ComuneRepository;
import com.example.comuneids2024.Repository.ContentRepository;
import com.example.comuneids2024.Repository.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContentController {

    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private POIRepository poiRepository;


    public void insertContentToPOI(String idComune, String idPOI, Content c)
    {
        Optional<Comune> optionalComune = this.comuneRepository.findById(idComune);
        Optional<POI> optionalPOI =this.poiRepository.findById(idPOI);
        if (optionalComune.isPresent()) {
            Comune comune = optionalComune.get();
            comune.getPOI(idPOI).addContent(c);
            POI poi=optionalPOI.get();
            poi.addContent(c);
            this.comuneRepository.save(comune);
            this.contentRepository.save(c);
            this.poiRepository.save(poi);
        } else {
            throw new RuntimeException("Comune with ID " + idComune + " not found.");
        }
    }

    public void insertContentToPOIPending(String idComune, String idPOI, Content c)
    {
        Optional<Comune> optionalComune = this.comuneRepository.findById(idComune);
        if (optionalComune.isPresent()) {
            Comune comune = optionalComune.get();
            comune.getPOI(idPOI).addContent(c);
            this.comuneRepository.save(comune);
            this.contentRepository.save(c);
        } else {
            throw new RuntimeException("Comune with ID " + idComune + " not found.");
        }
    }

    public Content viewContent(String contentID) {
        return contentRepository.findById(contentID).orElse(null);
    }

}
