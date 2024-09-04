package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.DTO.ContentDTO;
import com.example.comuneids2024.Model.DTO.POIDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Document(collection = "POIEvento")
public class POIEvento extends POI {

    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;

    public POIEvento(Coordinate coordinate) {
        super(coordinate);
        this.setTipo(Tipo.EVENTO);
    }

    public POIEvento() {
        super();
    }



    public void addDate(LocalDateTime dataInizio, LocalDateTime dataFine) {
        this.dataFine = dataFine;
        this.dataInizio = dataInizio;
    }

    @Override
    public void insertPOIInfo(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

    @Override
    public LocalTime[] getOpeningTime() {
        return null;
    }

    @Override
    public LocalTime[] getClosingTime() {
        return null;
    }

    @Override
    public  LocalDateTime getDataInizio() {
        return this.dataInizio;
    }

    @Override
    public  LocalDateTime getDataFine() {
        return this.dataFine;
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        super.setCoordinate(coordinate);
    }

    @Override
    public POIDTO getPOIInfo() {
        List<ContentDTO> contents = this.getContents().stream().map(Content::getContentInfo).toList();
        List<ContentDTO> pendingContentsGI = this.getContentsPending().stream().map(Content::getContentInfo).toList();
        return new POIDTO(this.getPOIId(), this.getName(), this.getDescription(), this.getCoord(), this.getTipo(), contents,pendingContentsGI,null,null,this.getDataInizio(),this.getDataFine());
    }
}
