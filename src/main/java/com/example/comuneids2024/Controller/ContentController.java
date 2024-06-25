package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.Comune;
import com.example.comuneids2024.Model.Content;
import com.example.comuneids2024.Model.Contest;
import com.example.comuneids2024.Repository.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentController {

    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ContentRepository contentRepository;


    public void insertContentToPOI(Long idComune, Long idPOI, Content c)
    {
        Comune comune=this.comuneRepository.findById(idComune).get();
        comune.getPOI(idPOI).addContent(c);
        this.comuneRepository.save(comune);
    }

    public void insertContentToPOIPending(Long idComune, Long idPOI, Content c)
    {
        Comune comune=this.comuneRepository.findById(idComune).get();
        comune.getPOI(idPOI).addContentPending(c);
        this.comuneRepository.save(comune);
    }

    public Content viewContent(Long contentID) {
        return contentRepository.findById(contentID).orElse(null);
    }

}
