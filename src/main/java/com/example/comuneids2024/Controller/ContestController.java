package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.*;
import com.example.comuneids2024.Model.DTO.ContestDTO;
import com.example.comuneids2024.Model.DTO.UtenteAutenticatoDTO;
import com.example.comuneids2024.Repository.ComuneRepository;
import com.example.comuneids2024.Repository.ContentRepository;
import com.example.comuneids2024.Repository.ContestRepository;
import com.example.comuneids2024.Repository.UtenteAutenticatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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



    //
    @PostMapping("/createContest")
    public ResponseEntity<Object> createContest(@RequestBody Contest contest, @RequestParam String comuneId) {
        // Recupera il comune dal repository
        boolean check=false;
        Comune comune = comuneRepository.findById(comuneId).orElse(null);
        List<UtenteAutenticato> utenti = utenteAutenticatoRepository.findAll();
        for(UtenteAutenticato u: contest.getUtentiInvitati())
        {
            if(!(u.getRole().equals(Role.CONTRIBUTOR)||(u.getRole().equals(Role.CONTRIBUTORAUTORIZZATO))))
            {
                return new ResponseEntity<>("L'utente non e' un contributor",HttpStatus.BAD_REQUEST);
            }
        }
        for (UtenteAutenticato u: utenti )
        {
            for(UtenteAutenticato uContest: contest.getUtentiInvitati())
            {
                if(u.getId().equals(uContest.getId()))
                {
                    check=true;
                }
            }
        }
        if(!check)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
        }
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
        if(!(contributor.getRole().equals(Role.CONTRIBUTOR)||(contributor.getRole().equals(Role.CONTRIBUTORAUTORIZZATO))))
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


}