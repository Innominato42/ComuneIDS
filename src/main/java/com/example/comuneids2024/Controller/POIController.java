package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.*;
import com.example.comuneids2024.Model.DTO.POIDTO;
import com.example.comuneids2024.Repository.ComuneRepository;


import com.example.comuneids2024.Repository.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class POIController {

    @Autowired
    private   ComuneRepository comuneRepository;

    @Autowired
    private POIRepository poiRepository;

    //TODO provare a cambiare il controllo instanceof
    public void insertPOI(String idComune, POIFactory p, POIDTO poigi)
    {
        POI poi= p.createPOI(poigi.getCoordinate());
        poi.insertPOIInfo(poigi.getName(),poigi.getDescription());
        if (poi instanceof POILuogoOra plo) {
            plo.insertTime(poigi.getOpeningTime(), poigi.getClosingTime());

        }
        if (poi instanceof POIEvento pe) {
            pe.addDate(poigi.getDateOpen(), poigi.getDateClose());
        }
        if(comuneRepository.findById(idComune).isPresent())
        {
            Comune c = comuneRepository.findById(idComune).get();
            c.addPOI(poi);
            poiRepository.save(poi);
            comuneRepository.save(c);
        }
        else {
            throw new RuntimeException();
        }
    }


    public  void insertPOIPending(String idComune, POIFactory p, POIDTO poigi) {
        POI poi = p.createPOI(poigi.getCoordinate());
        poi.insertPOIInfo(poigi.getName(), poigi.getDescription());
        if (poi instanceof POILuogoOra plo) {
            plo.insertTime(poigi.getOpeningTime(), poigi.getClosingTime());
        }
        if (poi instanceof POIEvento pe) {
            pe.addDate(poigi.getDateOpen(), poigi.getDateClose());
        }
        if(comuneRepository.findById(idComune).isPresent()) {
            Comune c = comuneRepository.findById(idComune).get();
            c.addPOIPending(poi);
            poiRepository.save(poi);
            comuneRepository.save(c);
        }
    }

    public POI viewPOI(String poiID) {
        return poiRepository.findById(poiID).orElse(null);
    }

}
