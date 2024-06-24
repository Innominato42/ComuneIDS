package com.example.comuneids2024.Model;

import com.example.comuneids2024.Controller.ContestController;
import com.example.comuneids2024.Repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContestManager {

    @Autowired
    private ContestRepository contestRepository;

    public void addContest(Contest contest) {
        this.contestRepository.save(contest);
    }

    /**
     * Trova e ritorna il contest con l id passato come parametro
     *
     * @param id del contest
     * @return contest richiesto
     */
    public Contest getContest(Long id) {
        return contestRepository.findById(id).orElse(null);
    }

    /**
     * Restituisce tutti i contest presenti nella piattaforma
     */
    public List<Contest> getAllContest() {
        List<Contest> contests = new ArrayList<Contest>();
        this.contestRepository.findAll().forEach(contest -> {
            contest.getContestInfo();
            contests.add(contest.getContestInfo());
        });
        return contests;
    }

    /**
     * Restituisce tutti i contest creati da un contributor specifico
     * @param contributorID id del contributor
     * @return la lista con tutti i contest del contributor
     */
    public List<Contest> getAllContest(Long contributorID)
    {

        List <Contest> contests = new ArrayList<>();
        this.contestRepository.findAll().forEach(contest -> {
            if((!contest.isClosed()) && contest.contributorInvited(contributorID)){
                contests.add(contest.getContestInfo());
            }
        });
        return contests;

    }

    public List<Contest> getAllOpenContest(){

        List <Contest> contests = new ArrayList<>();
        this.contestRepository.findAll().forEach(contest -> {
            if(!contest.isClosed())
            {
                contests.add(contest.getContestInfo());
            }
        });

        return contests;
    }
}