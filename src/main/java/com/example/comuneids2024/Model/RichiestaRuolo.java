package com.example.comuneids2024.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("collection= richiestaRuolo")
public class RichiestaRuolo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "richiestaRuolo_generator")
    private Long id;
    private Long idUtente;

    private Role ruoloRichiesto;

    public RichiestaRuolo(Long idUtente, Role ruoloRichiesto)
    {
        this.ruoloRichiesto=ruoloRichiesto;
        this.idUtente=idUtente;
    }

    public Long getId() {
        return id;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public Role getRuoloRichiesto() {
        return ruoloRichiesto;
    }
}
