package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.*;
import com.example.comuneids2024.Model.DTO.ContentDTO;
import com.example.comuneids2024.Model.DTO.UtenteAutenticatoDTO;
import com.example.comuneids2024.Repository.ComuneRepository;
import com.example.comuneids2024.Repository.ContentRepository;
import com.example.comuneids2024.Repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestController {


    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private UtenteAutenticatoManager utenteAutenticatoManager;

    public void createContest(Long idComune, Contest c, Long[] content)
    {
        for(Long i : content)
        {
            c.addContent(this.contentRepository.findById(i).orElse(null));
        }
        Comune comune= this.comuneRepository.findById(idComune).get();
        this.comuneRepository.save(comune);

    }

    public Contest viewContest(Long contestID) {
        return contestRepository.findById(contestID).orElse(null);
    }

    public List<UtenteAutenticatoDTO> selectedContestContibutors() {
        return this.utenteAutenticatoManager.getAllContributors();
    }

    public void inviteContributor(Long id, Long idContributor) {
        Contest contest = this.contestRepository.findById(id).get();
        contest.inviteContributor(this.utenteAutenticatoManager.getUtente(idContributor));
        this.contestRepository.save(contest);
    }






}
