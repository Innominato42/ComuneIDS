package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.Content;
import com.example.comuneids2024.Model.Contest;
import com.example.comuneids2024.Repository.ComuneRepository;
import com.example.comuneids2024.Repository.ContentRepository;
import com.example.comuneids2024.Repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.comuneids2024.Model.Comune;

@Service
public class ContestController {


    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ContentRepository contentRepository;

    public void createContest(Long idComune, Contest c, Long[] content)
    {
        for(Long i : content)
        {
            c.addContent(this.contentRepository.findById(i).orElse(null));
        }
        Comune comune= this.comuneRepository.findById(idComune).get();
        this.comuneRepository.save(comune);

    }




}
