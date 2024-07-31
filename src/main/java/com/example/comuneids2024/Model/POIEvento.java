package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.DTO.POIDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Document(collection = "POIEvento")
public class POIEvento extends POI {

    private LocalDateTime oraInizio;
    private LocalDateTime oraFine;

    public POIEvento(Coordinate coordinate) {
        super(coordinate);
        this.setTipo(Tipo.EVENTO);
    }

    public POIEvento() {
        super();
    }

    public LocalDateTime getOraInizio() {
        return oraInizio;
    }

    public LocalDateTime getOraFine() {
        return oraFine;
    }

    public void addDate(LocalDateTime oraInizio, LocalDateTime oraFine) {
        this.oraFine = oraFine;
        this.oraInizio = oraInizio;
    }

    @Override
    public void insertPOIInfo(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

    @Override
    public POIDTO getPOIInfo() {
        List<Content> contents = this.getContents().stream().map(c -> c.getContentInfo()).toList();
        List<Content> pendingContentsGI = this.getContentsPending().stream().map(pc -> pc.getContentInfo()).toList();
        return new POIDTO(this.getPOIId(), this.getName(), this.getDescription(), this.getCoordinate(), this.getTipo(), contents, pendingContentsGI);
    }
}
