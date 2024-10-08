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
    private ComuneRepository comuneRepository;

    @Autowired
    private POIRepository poiRepository;

    public void insertPOI(String idComune, POIFactory p, POIDTO poigi) {
        POI poi = p.createPOI(poigi.getCoordinate());
        poi.insertPOIInfo(poigi.getName(), poigi.getDescription());
        if (poi instanceof POILuogoOra plo) {
            plo.insertTime(poigi.getOpeningTime(), poigi.getClosingTime());

        }
        if (poi instanceof POIEvento pe) {
            pe.addDate(poigi.getDataInizio(), poigi.getDataFine());
        }
        if (comuneRepository.findById(idComune).isPresent()) {
            Comune c = comuneRepository.findById(idComune).get();
            System.out.println(c.getPOIValidate().size());
            c.addPOI(poi);
            poiRepository.save(poi);
            comuneRepository.save(c);
        } else {
            throw new RuntimeException();
        }
    }


    public void insertPOIPending(String idComune, POIFactory p, POIDTO poigi) {

        POI poi = p.createPOI(poigi.getCoordinate());
        poi.insertPOIInfo(poigi.getName(), poigi.getDescription());
        if (poi instanceof POILuogoOra plo) {
            plo.insertTime(poigi.getOpeningTime(), poigi.getClosingTime());

        }
        if (poi instanceof POIEvento pe) {
            pe.addDate(poigi.getDataInizio(), poigi.getDataFine());
        }
        if (comuneRepository.findById(idComune).isPresent()) {
            Comune c = comuneRepository.findById(idComune).get();
            System.out.println(c.getPOIValidate().size());
            c.addPOIPending(poi);
            poiRepository.save(poi);
            comuneRepository.save(c);
        } else {
            throw new RuntimeException();
        }
    }


}

