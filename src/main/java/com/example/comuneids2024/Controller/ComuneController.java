package com.example.comuneids2024.Controller;


import com.example.comuneids2024.Model.*;
import com.example.comuneids2024.Model.DTO.ComuneDTO;
import com.example.comuneids2024.Model.DTO.ItineraryDTO;
import com.example.comuneids2024.Model.DTO.POIDTO;
import com.example.comuneids2024.Repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comune")
public class ComuneController {


    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ItineraryController itineraryController;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private POIRepository POIRepository;

    @Autowired
    private ContentController contentController;

    @Autowired
    private ContestManager contestManager;

    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;

    @Autowired
    private POIController poiController;


    //Testato
    @PostMapping("/addComune")
    public ResponseEntity<String> addComune(@RequestBody ComuneDTO comuneDTO) {
        // Valida i dati del ComuneDTO
        if (comuneDTO.getNome() == null || comuneDTO.getNome().isEmpty()) {
            return new ResponseEntity<>("Nome del comune mancante", HttpStatus.BAD_REQUEST);
        }
        if (comuneDTO.getCoordinate() == null) {
            return new ResponseEntity<>("Coordinate del comune mancanti", HttpStatus.BAD_REQUEST);
        }
        if (comuneDTO.getCuratore() == null || comuneDTO.getCuratore().getId() == null) {
            return new ResponseEntity<>("Curatore del comune mancante", HttpStatus.BAD_REQUEST);
        }

        // Recupera il curatore dal repository
        Optional<UtenteAutenticato> curatoreOpt = utenteAutenticatoRepository.findById(comuneDTO.getCuratore().getId());
        if (curatoreOpt.isEmpty()) {
            return new ResponseEntity<>("Curatore non trovato", HttpStatus.BAD_REQUEST);
        }

        // Crea un nuovo oggetto Comune dall'oggetto ComuneDTO
        Comune comune = new Comune();
        comune.setNome(comuneDTO.getNome());
        comune.setCoordinate(comuneDTO.getCoordinate());
        comune.setCuratore(curatoreOpt.get());

        // Aggiungi le liste (validati, in attesa, ecc.) se presenti
        comune.setPOIValidate(comuneDTO.getPOIValidate());
        comune.setPOIAttesa(comuneDTO.getPOIAttesa());
        comune.setItinerarioValidato(comuneDTO.getItinerarioValidato());
        comune.setItinerarioAttesa(comuneDTO.getItinerarioAttesa());
        comune.setContests(comuneDTO.getContests());
        comune.setItinerariSegnalati(comuneDTO.getItinerariSegnalati());
        comune.setPOISegnalati(comuneDTO.getPOISegnalati());
        // Salva il comune nel repository
        comuneRepository.save(comune);

        // Ritorna una risposta di successo
        return new ResponseEntity<>("Comune aggiunto con successo", HttpStatus.OK);
    }

    //Testato
    @GetMapping("/getAllPOI")
    public ResponseEntity<Object> getAllPOI(@RequestParam String id) {

        if (this.comuneRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>("Errore : Comune non trovato", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(comuneRepository.findById(id).get().getAllPOI(), HttpStatus.OK);
        }

    }

    //Testato
    @GetMapping("/getAllPendingPOI")
    public ResponseEntity<Object> getAllPendingPOI(@RequestParam ("idComune") String idComune) {

        Comune comune= comuneRepository.findById(idComune).orElse(null);
        if(comune==null)
        {
            return new ResponseEntity<>("Comune non trovato", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comune.getAllPOIPending(),HttpStatus.OK);
    }

    @GetMapping("/getAllPendingContent")
    public ResponseEntity<Object> getAllPendingContent(@RequestParam ("idComune")String idComune, @RequestParam ("idPOI")String idPOI) {
        Comune comune= comuneRepository.findById(idComune).orElse(null);
        if(comune==null)
        {
            return new ResponseEntity<>("Comune non trovato", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comune.getPOI(idPOI).getContentsPending(),HttpStatus.OK);
    }

    //Testato
    @GetMapping("/getComune")
    public ResponseEntity<Object> getComune(@RequestParam String id) {
        return new ResponseEntity<>(comuneRepository.findById(id).get().getComune(), HttpStatus.OK);
    }

    //Testato
    @GetMapping("/getAllItinerary")
    public ResponseEntity<Object> getAllItinerary(@RequestParam String id) {
        if (this.comuneRepository.findById(id).isEmpty())
            return new ResponseEntity<>("Errore: Comune non trovato", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(this.comuneRepository.findById(id).get().getItinerarioValidato(), HttpStatus.OK);
    }

    //Testato
    @GetMapping("/getAllPendingItinerary")
    public ResponseEntity<Object> getAllPendingItinerary(@RequestParam String id) {
        if (this.comuneRepository.findById(id).isEmpty())
            return new ResponseEntity<>("Errore: Comune non trovato", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(this.comuneRepository.findById(id).get().getItinerarioAttesa(), HttpStatus.OK);
    }


    //Testato
    @PostMapping("/createItinerary")
    public ResponseEntity<Object> createItinerary(@RequestParam String idComune, @RequestBody ItineraryRequest request) {

        if (request.getPoi() == null || request.getPoi().length < 2) {
            return new ResponseEntity<>("Errore: Itinerario deve contenere almeno 2 POI", HttpStatus.BAD_REQUEST);
        }

        itineraryController.createItinerary(idComune, request.getItinerary(), request.getPoi());
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    //Testato
    @PostMapping("/createPendingItinerary")
    public ResponseEntity<Object> createPendingItinerary(@RequestParam String idComune, @RequestBody ItineraryRequest request) {
        if (request.getPoi() == null || request.getPoi().length < 2) {
            return new ResponseEntity<>("Errore: Itinerario deve contenere almeno 2 POI", HttpStatus.BAD_REQUEST);
        }
        itineraryController.createItineraryPending(idComune, request.getItinerary(), request.getPoi());
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    //Testato
    @PostMapping("/insertPOI")
    public ResponseEntity<Object> insertPOI(@RequestParam("comuneId") String idComune, @RequestBody POIDTO poi) {
        POIFactory pf;
        Tipo tipo = poi.getTipo();
        Comune comune= comuneRepository.findById(idComune).orElse(null);
        if(comune==null)
        {
            return new ResponseEntity<>("Comune non trovato",HttpStatus.NOT_FOUND);
        }
        if (tipo == null) {
            return new ResponseEntity<>("Errore : Tipo mancante", HttpStatus.BAD_REQUEST);
        }
        if (tipo.equals(Tipo.LUOGO)) {
            pf = new POILuogoFactory();
            POIDTO p = new POIDTO(poi.getId(), poi.getName(), poi.getDescription(), poi.getCoordinate(), tipo, poi.getContent(), poi.getContentPending(), null, null, null, null);
            poiController.insertPOI(idComune, pf, p);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } else if (tipo
                .equals(Tipo.EVENTO)) {
            pf = new POIEventoFactory();
            POIDTO p = new POIDTO(poi.getId(), poi.getName(), poi.getDescription(), poi.getCoordinate(), tipo, poi.getContent(), poi.getContentPending(), null, null, poi.getDataInizio(), poi.getDataFine());
            poiController.insertPOI(idComune, pf, p);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } else if (tipo
                .equals(Tipo.LUOGOCONORA)) {
            pf = new POILuogoOraFactory();
            POIDTO p = new POIDTO(poi.getId(), poi.getName(), poi.getDescription(), poi.getCoordinate(), tipo, poi.getContent(), poi.getContentPending(), poi.getOpeningTime(), poi.getClosingTime(), null, null);
            poiController.insertPOI(idComune, pf, p);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Errore : Tipo errato", HttpStatus.BAD_REQUEST);
        }

    }

    //Testato
    @PostMapping("/insertPOIPending")
    public ResponseEntity<Object> insertPOIPending(@RequestParam ("idComune") String id, @RequestBody POIDTO poi) {
        POIFactory pf;
        Tipo tipo = poi.getTipo();
        if (tipo == null) {
            return new ResponseEntity<>("Errore : Tipo mancante", HttpStatus.BAD_REQUEST);
        }
        if (tipo.equals(Tipo.LUOGO)) {
            pf = new POILuogoFactory();
            POIDTO p = new POIDTO(poi.getId(), poi.getName(), poi.getDescription(), poi.getCoordinate(), tipo, poi.getContent(), poi.getContentPending(), null, null, null, null);
            poiController.insertPOIPending(id, pf, p);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } else if (tipo
                .equals(Tipo.EVENTO)) {
            pf = new POIEventoFactory();
            POIDTO p = new POIDTO(poi.getId(), poi.getName(), poi.getDescription(), poi.getCoordinate(), tipo, poi.getContent(), poi.getContentPending(), null, null, poi.getDataInizio(), poi.getDataFine());
            poiController.insertPOIPending(id, pf, p);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } else if (tipo
                .equals(Tipo.LUOGOCONORA)) {
            pf = new POILuogoOraFactory();
            POIDTO p = new POIDTO(poi.getId(), poi.getName(), poi.getDescription(), poi.getCoordinate(), tipo, poi.getContent(), poi.getContentPending(), poi.getOpeningTime(), poi.getClosingTime(), null, null);
            poiController.insertPOIPending(id, pf, p);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Errore : Tipo errato", HttpStatus.BAD_REQUEST);
        }
    }

    //Testato
    @PostMapping("insertContentToPOI")
    public ResponseEntity<Object> insertContentToPOI(@RequestParam("idComune") String idComune, @RequestParam("idPOI") String id, @RequestPart("content") String contentJson, @RequestPart("file") MultipartFile file) throws JsonProcessingException {
        // Converti il JSON in un oggetto Content
        Content c = new ObjectMapper().readValue(contentJson, Content.class);
        try {
            c.addFile(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentController.insertContentToPOI(idComune, id, c);
        return new ResponseEntity<>("Content aggiunto con successo", HttpStatus.OK);
    }

    //Testato
    @PostMapping("insertPendingContentToPOI")
    public ResponseEntity<Object> insertPendingContentToPOI(@RequestParam("idComune") String idComune, @RequestParam("idPOI") String id, @RequestPart("content") String contentJson, @RequestPart("file") MultipartFile file) throws JsonProcessingException {
        Content c = new ObjectMapper().readValue(contentJson, Content.class);
        try {
            c.addFile(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentController.insertContentToPOIPending(idComune, id, c);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    //Testato
    @GetMapping("/getPOI")
    public ResponseEntity<Object> getPOI(@RequestParam("comuneId") String comuneId, @RequestParam("poiId") String poiId) {
        Optional<Comune> comuneOpt = comuneRepository.findById(comuneId);

        if (comuneOpt.isPresent()) {
            Comune comune = comuneOpt.get();
            // Trova il POI all'interno della lista dei POI del comune
            Optional<POI> poiOpt = comune.getAllPOI().stream()
                    .filter(poi -> poi.getPOIId().equals(poiId))
                    .findFirst();

            if (poiOpt.isPresent()) {
                POI poi = poiOpt.get();
                return new ResponseEntity<>(poi, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("POI not found in the specified comune", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Comune not found", HttpStatus.NOT_FOUND);
        }
    }


    //Testato
    @DeleteMapping("/deletePOI")
    public ResponseEntity<Object> deletePOI(@RequestParam("comuneID") String comuneId, @RequestParam("poiID") String poiId) {
        Optional<Comune> comuneOpt = comuneRepository.findById(comuneId);

        if (comuneOpt.isPresent()) {
            Comune comune = comuneOpt.get();

            Optional<POI> poiOpt = comune.getPOIValidate().stream()
                    .filter(poi -> poi.getPOIId().equals(poiId))
                    .findFirst();

            if (poiOpt.isPresent()) {
                POI poi = poiOpt.get();
                comune.getPOIValidate().remove(poi); // Rimuovi il POI dalla lista del comune

                // Salva il comune aggiornato nel repository
                comuneRepository.save(comune);

                // Elimina il POI dal repository dei POI
                POIRepository.delete(poi);

                return ResponseEntity.ok("POI eliminato dal comune con successo");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("POI non trovato nel comune");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comune non trovato");
        }
    }


    //Testato
    @DeleteMapping("/deleteContest")
    public ResponseEntity<Object> deleteContest(@RequestParam("contestID") String contestId, @RequestParam("comuneID") String comuneId) {
        // Recupera il comune dal repository
        Comune comune = comuneRepository.findById(comuneId).orElse(null);

        if (comune != null) {
            // Rimuove il contest dal comune
            boolean removed = comune.removeContest(contestId);

            if (removed) {
                // Elimina il contest dal repository dei contest
                contestManager.deleteContest(contestId);

                // Aggiorna il comune nel repository
                comuneRepository.save(comune);

                return ResponseEntity.ok("Contest eliminato dal comune con successo");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contest non trovato nel comune");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comune non trovato");
        }
    }

    //Testato
    @PostMapping("/segnalaPOI")
    public ResponseEntity<Object> segnalaPOI(@RequestParam("comuneID") String comuneId, @RequestParam("poiID") String poiId) {
        // Verifica l'esistenza del comune
        Comune comune = comuneRepository.findById(comuneId).orElse(null);
        if (comune == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comune non trovato");
        }

        // Verifica l'esistenza del POI
        POI poi = POIRepository.findById(poiId).orElse(null);
        if (poi == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("POI non trovato");
        }

        // Aggiungi il POI segnalato alla lista POISegnalati del comune
        comune.addPOISegnalato(poi);
        comuneRepository.save(comune);

        return ResponseEntity.ok("POI segnalato con successo");
    }

    //Testato
    @PostMapping("/segnalaItinerario")
    public ResponseEntity<Object> segnalaItinerario(@RequestParam String comuneId, @RequestParam String itinerarioId) {
        // Verifica l'esistenza del comune
        Comune comune = comuneRepository.findById(comuneId).orElse(null);
        if (comune == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comune non trovato");
        }

        // Verifica l'esistenza dell'Itinerario
        Itinerary itinerario = comune.getItinerary(itinerarioId);
        if (itinerario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Itinerario non trovato");
        }

        // Aggiungi l'Itinerario segnalato alla lista ItinerariSegnalati del comune
        comune.addItinerarioSegnalato(itinerario);
        comuneRepository.save(comune);

        return ResponseEntity.ok("Itinerario segnalato con successo");
    }

    //Testato
    @PostMapping("/modificaItinerario")
    public ResponseEntity<Object> modificaItinerario(@RequestParam("idComune") String idComune, @RequestParam("idItinerario") String idItinerario, @RequestParam("nome") String nome, @RequestParam("descrizione") String descrizione) {
        Comune comune = this.comuneRepository.findById(idComune).orElse(null);
        if (comune == null) {
            return new ResponseEntity<>("Comune non trovato", HttpStatus.NOT_FOUND);
        }
        Itinerary i = comune.getItinerary(idItinerario);
        if (i == null) {
            return new ResponseEntity<>("Itinerario non trovato", HttpStatus.NOT_FOUND);
        } else {
            i.addItineraryInfo(nome, descrizione);
            comuneRepository.save(comune);
            itineraryRepository.save(i);
            return new ResponseEntity<>("Itinerario modificato con successo", HttpStatus.OK);
        }

    }

    //Testato
    @PostMapping("/addPOItoItinerary")
    public ResponseEntity<Object> addPOItoItinerary(@RequestParam("idComune") String idComune, @RequestParam("idItinerary") String idItinerary, @RequestParam("idPOI") String idPOI) {
        Comune comune = comuneRepository.findById(idComune).orElse(null);
        if (comune == null) {
            return new ResponseEntity<>("Comune non trovato", HttpStatus.NOT_FOUND);
        }
        POI poi = comune.getPOI(idPOI);
        if (poi == null) {
            return new ResponseEntity<>("poi non trovato", HttpStatus.NOT_FOUND);
        }
        Itinerary itinerary = comune.getItinerary(idItinerary);
        for (POI p : itinerary.getPOIs()) {
            if (p.getPOIId().equals(poi.getPOIId())) {
                return new ResponseEntity<>("POI gia presente nell' itinerario", HttpStatus.BAD_REQUEST);
            }
        }
        itinerary.addPOI(poi);
        this.comuneRepository.save(comune);
        this.itineraryRepository.save(itinerary);
        return new ResponseEntity<>("POI aggiunto con successo", HttpStatus.OK);
    }

    //Testato
    @PostMapping("/removePOIfromItinerary")
    public ResponseEntity<Object> removePOIfromItinerary(@RequestParam("idComune") String idComune, @RequestParam("idItinerary") String idItinerary, @RequestParam("idPOI") String idPOI) {
        Comune comune = comuneRepository.findById(idComune).orElse(null);
        if (comune == null) {
            return new ResponseEntity<>("Comune non trovato", HttpStatus.NOT_FOUND);
        }

        Itinerary itinerary = comune.getItinerary(idItinerary);
        if (itinerary == null) {
            return new ResponseEntity<>("itinerario non trovato", HttpStatus.NOT_FOUND);
        }


        for (POI p : itinerary.getPOIs()) {
            if (p.getPOIId().equals(idPOI)) {
                itinerary.removePOI(idPOI);
                comuneRepository.save(comune);
                itineraryRepository.save(itinerary);
                return new ResponseEntity<>("il poi è stato eliminato con successo dall'itinerario", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("il poi non è presente nell'itinerario", HttpStatus.NOT_FOUND);
    }

    //Testato
    @PostMapping("/modificaContenuto")
    public ResponseEntity<Object> modificaContenuto(String idContenuto, String idComune, String nome, String descrizione) {
            Comune comune = comuneRepository.findById(idComune).orElse(null);
            Content content = contentRepository.findById(idContenuto).orElse(null);
            if (content == null) {
                return new ResponseEntity<>("Contenuto non trovato", HttpStatus.NOT_FOUND);
            }
            if (comune == null) {
                return new ResponseEntity<>("Comune non trovato", HttpStatus.NOT_FOUND);
            }
            List<POI> pois = comune.getAllPOI();
            int i = 0;
            for (POI p : pois) {
                if (!(p.getContents().isEmpty())) {

                    if ((idContenuto.equals(p.getContentIndex(i).getId()))) {
                        content.insertContentInfo(nome, descrizione);
                        comune.getPOI(p.getPOIId()).getContentIndex(i).insertContentInfo(nome, descrizione);
                        p.getContentIndex(i).insertContentInfo(nome, descrizione);
                        contentRepository.save(content);POIRepository.save(p);comuneRepository.save(comune);
                        List<Itinerary> itineraries = comune.getAllItinerary();
                        int j = 0;int k=0;
                        for (Itinerary I : itineraries) {
                            j = 0; k=0;
                            for (POI p1 : I.getPOIs()) {

                                if (!(p1.getContents().isEmpty())) {
                                    if (idContenuto.equals(p1.getContentIndex(j).getId())) {
                                        I.getPOIs().get(j).getContentIndex(k).insertContentInfo(nome,descrizione);
                                        itineraryRepository.save(I);
                                        return new ResponseEntity<>("Content modificato all interno dell itinerario e del poi", HttpStatus.OK);
                                    }
                                    k++;
                                    j++;
                                }
                            }
                        }
                        return new ResponseEntity<>("Content modificato in un POI", HttpStatus.OK);
                    }
                    i++;
                }

            }
            return new ResponseEntity<>("Content non trovato", HttpStatus.NOT_FOUND);
        }

        //Testato
    @PostMapping("/modificaPOI")
    public ResponseEntity<Object> modificaPOI(@RequestParam("idComune") String idComune, @RequestParam("idPOI") String id, @RequestParam("nome") String nome, @RequestParam("descrizione") String descrizione)
    {
        Comune comune= comuneRepository.findById(idComune).orElse(null);
        if(comune==null)
        {
            return  new ResponseEntity<>("Comune non trovato",HttpStatus.NOT_FOUND);
        }
        POI poi=comune.getPOI(id);
        if(poi==null)
        {
            return  new ResponseEntity<>("POI non trovato",HttpStatus.NOT_FOUND);
        }
        List<Itinerary> itineraries = comune.getAllItinerary();
        int cont = 0;
        for(Itinerary i : itineraries)
        {
            if(i.getPOIs().get(cont).getPOIId().equals(poi.getPOIId()))
            {
                comune.getAllPOI().get(cont).insertPOIInfo(nome,descrizione);
                poi.insertPOIInfo(nome,descrizione);
                i.getPOIs().get(cont).insertPOIInfo(nome,descrizione);
                itineraryRepository.save(i);
                comuneRepository.save(comune);
                POIRepository.save(poi);
                return new ResponseEntity<>("POI modificato con successo nell itinerario",HttpStatus.OK);
            }
        }
        poi.insertPOIInfo(nome,descrizione);
        comuneRepository.save(comune);
        POIRepository.save(poi);
        return ResponseEntity.ok("POI modificato con successo");

    }

    @PostMapping("/approvaPOI")
    public ResponseEntity<Object> approvaPOI(@RequestParam ("idComune")String idComune, @RequestParam ("idPOI") String idPOI)
    {
        Comune comune= comuneRepository.findById(idComune).orElse(null);
        if(comune==null)
        {
            return new ResponseEntity<>("Comune non trovato",HttpStatus.NOT_FOUND);
        }
        POI poi= comune.getPOIPending(idPOI);
        if(poi==null)
        {
            return new ResponseEntity<>("POI non trovato all interno del comune",HttpStatus.NOT_FOUND);
        }
        comune.getAllPOIPending().remove(poi);
        comune.addPOI(poi);
        comuneRepository.save(comune);
        return new ResponseEntity<>("poi approvato con successo",HttpStatus.OK);

    }

    @PostMapping("/approvaItinerario")
    public ResponseEntity<Object> approvaItinerario(@RequestParam ("idComune")String idComune, @RequestParam ("idItinerario") String idItinerario)
    {
        Comune comune= comuneRepository.findById(idComune).orElse(null);
        if(comune==null)
        {
            return new ResponseEntity<>("Comune non trovato",HttpStatus.NOT_FOUND);
        }
        Itinerary itinerary = comune.getItinerary(idItinerario);
        if(itinerary==null)
        {
            return new ResponseEntity<>("itinerario non trovato",HttpStatus.NOT_FOUND);
        }
        comune.getItinerarioAttesa().remove(itinerary);
        comune.addItinerary(itinerary);
        comuneRepository.save(comune);
        return new ResponseEntity<>("itinerario approvato con successo",HttpStatus.OK);
    }

    @PostMapping("/approvaContent")
    public ResponseEntity<Object> approvaContent(@RequestParam("idComune") String idComune,@RequestParam("idContent") String idContent,@RequestParam("idPOI") String idPOI)
    {
        Comune comune= comuneRepository.findById(idComune).orElse(null);
        if(comune==null)
        {
            return new ResponseEntity<>("Comune non trovato",HttpStatus.NOT_FOUND);
        }
        Content content =comune.getPOI(idPOI).getContentById(idContent);
        comune.getPOI(idPOI).getContentsPending().remove(content);
        comune.getPOI(idPOI).addContent(content);
        comuneRepository.save(comune);
        POIRepository.save(comune.getPOI(idPOI));
        return new ResponseEntity<>("Contenuto approvato con successo",HttpStatus.OK);


    }


}



