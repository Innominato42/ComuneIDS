package com.example.comuneids2024.Controller;


import com.example.comuneids2024.Model.*;
import com.example.comuneids2024.Model.DTO.ComuneDTO;
import com.example.comuneids2024.Model.DTO.POIDTO;
import com.example.comuneids2024.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private POIRepositoriy POIRepository;

    @Autowired
    private ContentController contentController;

    @Autowired
    private ContestManager contestManager;

    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;

    @Autowired
    private POIController poiController;


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

        // Salva il comune nel repository
        comuneRepository.save(comune);

        // Ritorna una risposta di successo
        return new ResponseEntity<>("Comune aggiunto con successo", HttpStatus.OK);
    }


    @GetMapping("/getAllPOI")
    public ResponseEntity<Object> getAllPOI(String id){

        if(this.comuneRepository.findById(id).isEmpty())
        {
            return new ResponseEntity<>("Errore : Comune non trovato",HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(comuneRepository.findById(id).get().getAllPOI(), HttpStatus.OK);
        }

    }

    @GetMapping("/getAllPendingPOI")
    public ResponseEntity<Object> getAllPendingPOI(String id){

        if(this.comuneRepository.findById(id).isEmpty())
        {
            return new ResponseEntity<>("Errore : Comune non trovato",HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(comuneRepository.findById(id).get().getAllPOIPending(), HttpStatus.OK);
        }
    }

    @GetMapping("/getAllItinerary")
    public ResponseEntity<Object> getAllItinerary(@RequestParam("comuneId") String id) {
        if (this.comuneRepository.findById(id).isEmpty())
            return new ResponseEntity<>("Errore: Comune non trovato", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(this.comuneRepository.findById(id).get().getItinerarioValidato(), HttpStatus.OK);
    }

    @GetMapping("/getAllPendingItinerary")
    public ResponseEntity<Object> getAllPendingItinerary(@RequestParam("comuneId") Long id) {
        if (this.comuneRepository.findById(id).isEmpty())
            return new ResponseEntity<>("Errore: Comune non trovato", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(this.comuneRepository.findById(id).get().getItinerarioAttesa(), HttpStatus.OK);
    }


    @PostMapping("/createItinerary")
    public ResponseEntity<Object> createItinerary(String idComune, Itinerary i, String [] poi)
    {
        if(poi.length < 2)
            return new ResponseEntity<>("Errore: Itinerario deve contenere almeno 2 POI", HttpStatus.BAD_REQUEST);
        itineraryController.createItinerary(idComune, i, poi);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/createPendingItinerary")
    public ResponseEntity<Object> createPendingItinerary(String idComune, Itinerary i, String [] poi)
    {
        if(poi.length < 2)
            return new ResponseEntity<>("Errore: Itinerario deve contenere almeno 2 POI", HttpStatus.BAD_REQUEST);
        itineraryController.createItineraryPending(idComune, i, poi);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }



    @GetMapping("/insertPOI")
    public ResponseEntity<Object> insertPOI(String id, POI poi)
    {
        POIFactory pf;
        if(poi.getTipo().equals(Tipo.LUOGO))
        {
            pf=new POILuogoFactory();
        }
        else if(poi.getTipo().equals(Tipo.EVENTO))
        {
            pf=new POIEventoFactory();
        }
        else if(poi.getTipo().equals(Tipo.LUOGOCONORA))
        {
            pf=new POILuogoOraFactory();
        }
        else
        {
            return new ResponseEntity<>("Errore : Tipo errato", HttpStatus.BAD_REQUEST);
        }
        POIDTO p = new POIDTO(poi.getPOIId(), poi.getName(), poi.getDescription(),poi.getCoordinate(), poi.getTipo(), poi.getContents(), poi.getContentsPending());
        poiController.insertPOI(id, pf, p);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    @GetMapping("/insertPOIPending")
    public ResponseEntity<Object> insertPOIPending(String id, POI poi)
    {
        POIFactory pf;
        if(poi.getTipo().equals(Tipo.LUOGO))
        {
            pf=new POILuogoFactory();
        }
        else if(poi.getTipo().equals(Tipo.EVENTO))
        {
            pf=new POIEventoFactory();
        }
        else if(poi.getTipo().equals(Tipo.LUOGOCONORA))
        {
            pf=new POILuogoOraFactory();
        }
        else
        {
            return new ResponseEntity<>("Errore : Tipo errato", HttpStatus.BAD_REQUEST);
        }
        POIDTO p = new POIDTO(poi.getPOIId(), poi.getName(), poi.getDescription(),poi.getCoordinate(), poi.getTipo(), poi.getContents(), poi.getContentsPending());
        poiController.insertPOIPending(id, pf, p);
        return new ResponseEntity<>("ok", HttpStatus.OK);

    }

    @PostMapping("insertContentToPOI")
    public ResponseEntity<Object> insertContentToPOI(@RequestParam("idComune") String idComune, @RequestParam("idPOI") String id,  @RequestPart("content") Content c, @RequestPart("file") MultipartFile file)
    {
        try {
            c.addFile(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentController.insertContentToPOI(idComune, id, c);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("insertPendingContentToPOI")
    public ResponseEntity<Object> insertPendingContentToPOI(@RequestParam("idComune") String idComune, @RequestParam("idPOI") String id,  @RequestPart("content") Content c, @RequestPart("file") MultipartFile file)
    {
        try {
            c.addFile(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentController.insertContentToPOIPending(idComune, id, c);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }





    @GetMapping("/getPOI/{comuneId}/{poiId}")
    public ResponseEntity<Object> getPOI(@PathVariable Long comuneId, @PathVariable Long poiId) {
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


    @DeleteMapping("/deletePOI/{comuneId}/{poiId}")
    public ResponseEntity<Object> deletePOI(@PathVariable Long comuneId, @PathVariable Long poiId) {
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

    @PostMapping("/{comuneId}/addContest")
    public ResponseEntity<Object> addContest(@RequestBody Contest contest, @PathVariable Long comuneId) {
        // Recupera il comune dal repository
        Comune comune = comuneRepository.findById(comuneId).orElse(null);

        if (comune != null) {
            // Aggiunge il contest al comune
            comune.addContest(contest);
            contestManager.addContest(contest); // Salva il contest nel repository dei contest

            // Aggiorna il comune nel repository
            comuneRepository.save(comune);

            return ResponseEntity.ok("Contest aggiunto al comune con successo");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comune non trovato");
        }
    }

    @DeleteMapping("/{comuneId}/deleteContest/{contestId}")
    public ResponseEntity<Object> deleteContest(@PathVariable Long contestId, @PathVariable Long comuneId) {
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


    @PostMapping("/segnalaPOI/{comuneId}/{poiId}")
    public ResponseEntity<Object> segnalaPOI(@PathVariable Long comuneId, @PathVariable Long poiId) {
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

    @PostMapping("/segnalaItinerario/{comuneId}/{itinerarioId}")
    public ResponseEntity<Object> segnalaItinerario(@PathVariable Long comuneId, @PathVariable Long itinerarioId) {
        // Verifica l'esistenza del comune
        Comune comune = comuneRepository.findById(comuneId).orElse(null);
        if (comune == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comune non trovato");
        }

        // Verifica l'esistenza dell'Itinerario
        Itinerary itinerario = itineraryRepository.findById(itinerarioId).orElse(null);
        if (itinerario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Itinerario non trovato");
        }

        // Aggiungi l'Itinerario segnalato alla lista ItinerariSegnalati del comune
        comune.addItinerarioSegnalato(itinerario);
        comuneRepository.save(comune);

        return ResponseEntity.ok("Itinerario segnalato con successo");
    }

    @PostMapping("/modificaItinerario")
    public ResponseEntity<Object> modificaItinerario(Long id, String nome, String descrizione)
    {
        Itinerary i= this.itineraryRepository.findById(id).orElse(null);
        if(i==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Itinerario non trovato");
        }
        else {
            i.addItineraryInfo(nome, descrizione);
            itineraryRepository.save(i);
            return ResponseEntity.ok("Itinerario modificato con successo");
        }

    }
    @PostMapping("/modificaContenuto")
    public ResponseEntity<Object> modificaContenuto(Long id, String nome, String descrizione)
    {
        Content i= this.contentRepository.findById(id).orElse(null);
        if(i==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contenuto non trovato");
        }
        else {
            i.insertContentInfo(nome,descrizione);
            contentRepository.save(i);
            return ResponseEntity.ok("Contenuto modificato con successo");
        }

    }
    @PostMapping("/modificaPOI")
    public ResponseEntity<Object> modificaPOI(Long id, String nome, String descrizione)
    {
        POI poi=this.POIRepository.findById(id).orElse(null);
        if(poi==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("POI non trovato");
        }
        else {
            poi.insertPOIInfo(nome,descrizione);
            POIRepository.save(poi);
            return ResponseEntity.ok("POI modificato con successo");
        }

    }

}



