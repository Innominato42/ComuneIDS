package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.*;
import com.example.comuneids2024.Model.DTO.ContentDTO;
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


    @PostMapping("/createContest")
    public ResponseEntity<Object> createContest(@RequestParam("idComune") String idComune, @RequestBody Contest c, @RequestParam("content") String[] content) {
        for (String i : content) {
            c.addContent(this.contentRepository.findById(i).orElse(null));
        }
        Comune comune = this.comuneRepository.findById(idComune).orElse(null);
        if (comune == null) {
            return new ResponseEntity<>("Comune not found", HttpStatus.NOT_FOUND);
        }
        comune.addContest(c);
        this.comuneRepository.save(comune);
        return new ResponseEntity<>("Contest created successfully", HttpStatus.OK);
    }



    @GetMapping("/viewContest/{contestID}")
    public ResponseEntity<Contest> viewContest(@PathVariable String contestID) {
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
    public ResponseEntity<String> inviteContributor(@RequestParam String id, @RequestParam String idContributor) {
        Contest contest = this.contestRepository.findById(id).orElse(null);
        if (contest == null) {
            return new ResponseEntity<>("Contest non trovato", HttpStatus.NOT_FOUND);
        }
        contest.inviteContributor(this.utenteAutenticatoManager.getUtente(idContributor));
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