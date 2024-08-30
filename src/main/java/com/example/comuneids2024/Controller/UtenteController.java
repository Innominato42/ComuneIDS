package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.*;
import com.example.comuneids2024.Model.DTO.UtenteAutenticatoDTO;
import com.example.comuneids2024.Repository.ComuneRepository;
import com.example.comuneids2024.Repository.UtenteAutenticatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Utente")
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

    @Autowired
    private RegistrazioneController registrazioneController;



    //Testato
    @PostMapping("/addPOIToFavorites")
    public ResponseEntity<Object> addPOIToFavorites(@RequestParam String idUtente, @RequestParam("POIid") String POIid, @RequestParam("idComune") String idComune) {
        if(comuneRepository.findById(idComune).get().getPOI(POIid) == null){
            return new ResponseEntity<>("POI non presente nel comune", HttpStatus.BAD_REQUEST);
        }
        UtenteAutenticato utente =utenteAutenticatoRepository.findById(idUtente).orElse(null);
        if(utente==null)
        {
            return new ResponseEntity<>("Utente non trovato",HttpStatus.NOT_FOUND);
        }
        if (this.preferitiManager.addPOItoFavourites(idUtente, idComune, POIid)) {
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("POI già presente tra i preferiti", HttpStatus.BAD_REQUEST);
        }
    }

    //Testato
    @PostMapping("/addItineraryToFavorites")
    public ResponseEntity<Object> addItineraryToFavorites(@RequestParam("utenteId") String idUtente ,@RequestParam("itineraryId") String itineraryId, @RequestParam("idComune") String idComune) {
        if(comuneRepository.findById(idComune).get().getItinerary(itineraryId) == null){
            return new ResponseEntity<>("Itinerario non presente nel comune", HttpStatus.BAD_REQUEST);
        }
        UtenteAutenticato utente = utenteAutenticatoRepository.findById(idUtente).orElse(null);
        if(utente==null)
        {
            return new ResponseEntity<>("Utente non trovato",HttpStatus.NOT_FOUND);
        }
        if (this.preferitiManager.addItineraryToFavourites(idUtente, idComune, itineraryId)) {
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Itinerario già presente tra i preferiti o inesistente", HttpStatus.BAD_REQUEST);
        }
    }

    //Testato
    @PutMapping ("/changeRole")
    public ResponseEntity<Object> changeRole(@RequestParam("idUtente") String idUtente, @RequestParam("role") Role role) {
        if(role.equals(Role.GESTORE) || role.equals(Role.CURATORE)){
            return new ResponseEntity<>("Ruolo non assegnabile", HttpStatus.BAD_REQUEST);
        }
        this.utenteAutenticatoManager.cambiaRuolo(idUtente,role);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    //Testato
    @GetMapping("/viewAllUsers")
    public ResponseEntity<Object> viewAllUsers() {
        return new ResponseEntity<>(this.utenteAutenticatoManager.viewAllUser(), HttpStatus.OK);
    }

    @GetMapping("/viewRegistrationUsers")
    public ResponseEntity<Object> viewRegistrationUsers() {
        return new ResponseEntity<>(this.utenteAutenticatoManager.viewRegistrationUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/gestore/refuseRegistration")
    public ResponseEntity<Object> refuseRegistration(@RequestParam("id") String id) {
        this.registrazioneController.refuseRegistration(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    @PutMapping("/gestore/approveRegistration")
    public ResponseEntity<Object> approveRegistration(@RequestParam("id") String id) {
        this.registrazioneController.approveRegistration(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    //Testato
    @PostMapping("/gestore/rendiContributor/Autorizzato")
    private ResponseEntity<Object> rendiContributorAutorizzato(@RequestParam("id") String id)
    {
        UtenteAutenticato utente= utenteAutenticatoRepository.findById(id).orElse(null);
        if(!(utente.getRole().equals(Role.CONTRIBUTOR)))
        {
            return new ResponseEntity<>("L'utente non e' un contributor o e' gia un contributor autorizzato",HttpStatus.BAD_REQUEST);
        }

        this.roleManager.nuovoRuolo(id, Role.valueOf("CONTRIBUTORAUTORIZZATO"));
        return new ResponseEntity<>("ok",HttpStatus.OK);
    }


    //Testato
    @PostMapping("/requestChangeRole")
    public ResponseEntity<Object> requestChangeRole(@RequestParam("idUtente") String idUtente, @RequestParam ("ruolo") Role ruolo) {
        //UtenteAutenticato utente= this.utenteAutenticatoRepository.findById(id).orElse(null);
        if(this.roleManager.viewChangeRoleRequests().stream().anyMatch(x -> x.getId().equals(idUtente))){
            return new ResponseEntity<>("Richiesta già inviata", HttpStatus.BAD_REQUEST);
        }
        this.roleManager.nuovaRichiestaCambioRuolo(idUtente,ruolo);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    //Testato
    @GetMapping("viewChangeRoleRequests")
    public ResponseEntity<Object> viewChangeRoleRequests() {
        return new ResponseEntity<>(this.roleManager.viewChangeRoleRequests(), HttpStatus.OK);
    }


    //Testato
    @DeleteMapping("/rifiutaRichiesta")
    public ResponseEntity<Object> rifiutaRichiesta(@RequestParam("id") String id) {
        this.roleManager.rifiutaRichiesta(id);
        return new ResponseEntity<>("Richiesta rifiutata", HttpStatus.OK);
    }


    //Testato
    @PutMapping("/approvaRichiesta")
    public ResponseEntity<Object> approvaRichiesta(@RequestParam("id") String id) {
        this.roleManager.approvaRichiesta(id);
        return new ResponseEntity<>("Richiesta approvata", HttpStatus.OK);
    }


    //Testato
    @PostMapping("/registrazioneUtente")
    public ResponseEntity<Object> registrationUser(@RequestBody UtenteAutenticatoDTO utente) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                return new ResponseEntity<>("Utente già autenticato o ruolo non disponibile alla registrazione", HttpStatus.BAD_REQUEST);
            }
            if (this.utenteAutenticatoManager.containsUtente(utente.getEmail(), utente.getUsername())) {
                return new ResponseEntity<>("Username e/o email già utilizzate", HttpStatus.BAD_REQUEST);
            }
            System.out.println("Utente: " + utente.getEmail() + ", " + utente.getUsername());
            if(this.registrazioneController.registrationUser(utente.getEmail(), utente.getUsername(), utente.getPassword(), utente.getRuolo()))
                return new ResponseEntity<>("ok", HttpStatus.OK);
            else
                return new ResponseEntity<>("Scegli un ruolo che sia turista autenticato o contributor", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Errore interno del server", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
