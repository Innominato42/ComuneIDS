package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.*;
import com.example.comuneids2024.Model.DTO.ContestDTO;
import com.example.comuneids2024.Model.DTO.UtenteAutenticatoDTO;
import com.example.comuneids2024.Repository.ComuneRepository;
import com.example.comuneids2024.Repository.ContentRepository;
import com.example.comuneids2024.Repository.ContestRepository;
import com.example.comuneids2024.Repository.UtenteAutenticatoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/contest")
public class ContestController {


    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;

    @Autowired
    private UtenteAutenticatoManager utenteAutenticatoManager;

    @Autowired
    private ContestManager contestManager;

    @Autowired
    private ContentController contentController;



    //Testato
    @PostMapping("/createContest")
    public ResponseEntity<Object> createContest(@RequestBody Contest contest, @RequestParam String comuneId) {
        // Recupera il comune dal repository
        boolean check=false;
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



    @GetMapping("/viewContest")
    public ResponseEntity<Contest> viewContest(@RequestParam String contestID) {
        Contest contest = contestRepository.findById(contestID).orElse(null);
        if (contest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contest, HttpStatus.OK);
    }

    @GetMapping("/selectedContestContributors")
    public ResponseEntity<List<UtenteAutenticatoDTO>> selectedContestContributors() {
        List<UtenteAutenticatoDTO> contributors = this.utenteAutenticatoManager.getAllContributors();
        return new ResponseEntity<>(contributors, HttpStatus.OK);
    }


    //Testato
    @PostMapping("/inviteContributor")
    public ResponseEntity<String> inviteContributor(@RequestParam String idContest,@RequestParam String idComune, @RequestParam String idContributor) {
        Contest contest = this.contestRepository.findById(idContest).orElse(null);
        Comune comune =this.comuneRepository.findById(idComune).orElse(null);
        UtenteAutenticato contributor = utenteAutenticatoRepository.findById(idContributor).orElse(null);

        //verifica che l'utente inserito esista
        if(contributor == null){
            return new ResponseEntity<>("L'utente non e' stato trovato",HttpStatus.NOT_FOUND);
        }
        //verifica che l'utente inserito sia effettivamente un contributor
        if(!(isContributor(contributor)))
        {
            return new ResponseEntity<>("L'utente non e' un contributor",HttpStatus.BAD_REQUEST);
        }
        //verifica che il comune inserito esista
        if(comune == null)
        {
            return new ResponseEntity<>("Comune non trovato",HttpStatus.NOT_FOUND);
        }
        //verifica che il contest inserito esista
        if (contest == null) {
            return new ResponseEntity<>("Contest non trovato", HttpStatus.NOT_FOUND);
        }
        contest.inviteContributor(this.utenteAutenticatoManager.getUtente(idContributor));
        if(comune.getContest(idContest)!= null)
        {
            comune.getContest(idContest).addUtenteInvitato(utenteAutenticatoManager.getUtente(idContributor));
        }
        this.comuneRepository.save(comune);
        this.contestRepository.save(contest);
        return new ResponseEntity<>("Contributor invitato con successo", HttpStatus.OK);
    }


    @PostMapping("/insertContentInfo")
    public ResponseEntity<String> insertContentInfo(@RequestParam String idContest, @RequestParam String nome,
                                                    @RequestParam String descrizione, @RequestParam boolean onInvite,
                                                    @RequestBody List<Content> content) {
        Contest contest = this.contestRepository.findById(idContest).orElse(null);
        if (contest == null) {
            return new ResponseEntity<>("Contest non trovato", HttpStatus.NOT_FOUND);
        }
        contest.addContestInfo(nome, descrizione, onInvite, content);
        this.contestRepository.save(contest);
        return new ResponseEntity<>("Informazioni del contest aggiunte con successo", HttpStatus.OK);
    }


    // Metodo per selezionare tutti i contenuti di un contest specifico
    @GetMapping("/selezionaContestContent/{contestID}")
    public ResponseEntity<List<Content>> selezionaContestContent(@PathVariable String contestID) {
        Contest contest = contestRepository.findById(contestID).orElse(null);
        if (contest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contest.getContents(), HttpStatus.OK);
    }


    @PostMapping("/selezionaContentVincitore")
    public ResponseEntity<String> selezionaContentVincitore(@RequestParam String contestID, @RequestParam String contentID) {
        Contest contest = contestRepository.findById(contestID).orElse(null);
        if (contest == null) {
            return new ResponseEntity<>("Contest non trovato", HttpStatus.NOT_FOUND);
        }

        String emailAutore = contest.getAutoreContentEmail(contentID);
        UtenteAutenticato vincitore = utenteAutenticatoRepository.findByEmail(emailAutore);

        if (vincitore == null) {
            return new ResponseEntity<>("Utente non trovato con l'email specificata", HttpStatus.NOT_FOUND);
        }
        contest.closeContest();
        contest.setVincitore(vincitore);
        contestRepository.save(contest);
        return new ResponseEntity<>("Vincitore selezionato con successo", HttpStatus.OK);
    }




    //Testato
    @PostMapping("insertContentToContest")
    public ResponseEntity<Object> insertContentToContest(@RequestParam("idComune") String idComune, @RequestParam("idContest") String idContest,  @RequestPart("content") String contentJson, @RequestPart("file") MultipartFile file) throws JsonProcessingException {
        // Converti il JSON in un oggetto Content
        Content c = new ObjectMapper().readValue(contentJson, Content.class);
        Contest contest=contestRepository.findById(idContest).orElse(null);
        //controlla se è presete la mail del creatore del content
        if(c.getCreatore().getEmail() == null){
            return new ResponseEntity<>("E' necessario inserire la mail del creatore del content",HttpStatus.BAD_REQUEST);
        }
        //controlla se il contributor è stato invitato a partecipare al contest
        boolean check=false;
        for(UtenteAutenticato u: contest.getUtentiInvitati())
        {
            if(u.getId().equals(c.getCreatore().getId()))
            {
                check=true;
            }
        }
        if(!check)
        {
            return new ResponseEntity<>("Il creatore del content non e' stato invitato a pratecipare al contest",HttpStatus.BAD_REQUEST);
        }
        //verifica che l'utente inserito sia effettivamente un contributor
        if(!(isContributor(c.getCreatore())))
        {
            return new ResponseEntity<>("L'utente non e' un contributor",HttpStatus.BAD_REQUEST);
        }
        //controlla se il content è già presente nel contest
        for(Content i : contest.getContents()) {
            if(i.getNome().equals(c.getNome()))
            {
                return new ResponseEntity<>("Content gia presente",HttpStatus.BAD_REQUEST);

            }
        }

        try {
            c.addFile(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        contentController.insertContentToContest(idComune, idContest, c);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    /*
    Controlla se l utente passato e' un contributor
     */
    public boolean isContributor(UtenteAutenticato utente)
    {
        if((utente.getRole().equals(Role.CONTRIBUTOR)||(utente.getRole().equals(Role.CONTRIBUTORAUTORIZZATO))))
        {
            return true;
        }
        return false;
    }


}