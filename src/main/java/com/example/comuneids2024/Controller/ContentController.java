package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.Comune;
import com.example.comuneids2024.Model.Content;
import com.example.comuneids2024.Model.Contest;
import com.example.comuneids2024.Repository.ComuneRepository;
import com.example.comuneids2024.Repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContentController {

    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ContentRepository contentRepository;


    public void insertContentToPOI(String idComune, String idPOI, Content c)
    {
        Optional<Comune> optionalComune = this.comuneRepository.findById(idComune);
        if (optionalComune.isPresent()) {
            Comune comune = optionalComune.get();
            comune.getPOI(idPOI).addContent(c);
            this.comuneRepository.save(comune);
        } else {
            throw new RuntimeException("Comune with ID " + idComune + " not found.");
        }
    }

    public void insertContentToPOIPending(String idComune, String idPOI, Content c)
    {
        Comune comune=this.comuneRepository.findById(idComune).get();
        comune.getPOI(idPOI).addContentPending(c);
        this.comuneRepository.save(comune);
    }

    public Content viewContent(String contentID) {
        return contentRepository.findById(contentID).orElse(null);
    }

}
