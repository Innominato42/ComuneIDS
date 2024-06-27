package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.*;
import com.example.comuneids2024.Model.DTO.POIDTO;
import com.example.comuneids2024.Repository.ComuneRepository;
import com.example.comuneids2024.Repository.POIRepositoriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class POIController {

    @Autowired
    private   ComuneRepository comuneRepository;

    @Autowired
    private POIRepositoriy poiRepository;

    public void insertPOI(Long idComune, POIFactory p, POIDTO poigi)
    {
        POI poi= p.createPOI(poigi.getCoordinate());
        poi.insertPOIInfo(poigi.getName(),poigi.getDescription());
        if (poi instanceof POILuogoOra plo) {
            plo.insertTime(poigi.getOpeningTime(), poigi.getClosingTime());
        }
        if (poi instanceof POIEvento pe) {
            pe.addDate(poigi.getDateOpen(), poigi.getDateClose());
        }
        Comune c = comuneRepository.findById(idComune).get();
        c.addPOI(poi);
        comuneRepository.save(c);

    }


    public  void insertPOIPending(Long idComune, POIFactory p, POIDTO poigi) {
        POI poi = p.createPOI(poigi.getCoordinate());
        poi.insertPOIInfo(poigi.getName(), poigi.getDescription());
        if (poi instanceof POILuogoOra plo) {
            plo.insertTime(poigi.getOpeningTime(), poigi.getClosingTime());
        }
        if (poi instanceof POIEvento pe) {
            pe.addDate(poigi.getDateOpen(), poigi.getDateClose());
        }
        Comune c = comuneRepository.findById(idComune).get();
        c.addPOIPending(poi);
        comuneRepository.save(c);
    }

    public POI viewPOI(Long poiID) {
        return poiRepository.findById(poiID).orElse(null);
    }

}
