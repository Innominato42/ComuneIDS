package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.PreferitiManager;
import com.example.comuneids2024.Model.Role;
import com.example.comuneids2024.Model.RoleManager;
import com.example.comuneids2024.Model.UtenteAutenticatoManager;
import com.example.comuneids2024.Repository.ComuneRepository;
import com.example.comuneids2024.Repository.UtenteAutenticatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UtenteController {

    @Autowired
    private ComuneRepository comuneRepository;
    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;

    @Autowired
    private PreferitiManager preferitiManager;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private UtenteAutenticatoManager utenteAutenticatoManager;

    @PostMapping("addPOIToFavorites")
    public ResponseEntity<Object> addPOIToFavorites(Authentication authentication, @RequestParam("POIid") Long POIid, @RequestParam("idComune") Long idComune) {
        if(comuneRepository.findById(idComune).get().getPOI(POIid) == null){
            return new ResponseEntity<>("POI non presente nel comune", HttpStatus.BAD_REQUEST);
        }
        Long id = this.utenteAutenticatoRepository.findByUsername(authentication.getName()).getId();
        if (this.preferitiManager.addPOItoFavourites(id, POIid, idComune)) {
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("POI già presente tra i preferiti", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("addItineraryToFavorites")
    public ResponseEntity<Object> addItineraryToFavorites(Authentication authentication, @RequestParam("itineraryId") Long itineraryId, @RequestParam("idComune") Long idComune) {
        if(comuneRepository.findById(idComune).get().getItinerary(itineraryId) == null){
            return new ResponseEntity<>("Itinerario non presente nel comune", HttpStatus.BAD_REQUEST);
        }
        Long id = this.utenteAutenticatoRepository.findByUsername(authentication.getName()).getId();
        if (this.preferitiManager.addPOItoFavourites(id, itineraryId, idComune)) {
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Itinerario già presente tra i preferiti o inesistente", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping ("changeRole")
    public ResponseEntity<Object> changeRole(@RequestParam("id") Long id, @RequestParam("role") Role role) {
        if(role.equals(Role.GESTORE) || role.equals(Role.CURATORE)){
            return new ResponseEntity<>("Ruolo non assegnabile", HttpStatus.BAD_REQUEST);
        }
        this.utenteAutenticatoManager.cambiaRuolo(id,role);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("viewAllUsers")
    public ResponseEntity<Object> viewAllUsers() {
        return new ResponseEntity<>(this.utenteAutenticatoManager.viewAllUser(), HttpStatus.OK);
    }


}
