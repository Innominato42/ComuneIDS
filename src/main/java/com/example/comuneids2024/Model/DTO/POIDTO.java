package com.example.comuneids2024.Model.DTO;

import com.example.comuneids2024.Model.Content;
import com.example.comuneids2024.Model.Coordinate;
import com.example.comuneids2024.Model.Tipo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

//TODO CAPIRE SE QUESTO TIPO DI CLASSE SERVE VERAMENTE
public class POIDTO {
    private final String id;
    private final String name;
    private final String description;
    private final Coordinate coordinate;
    private final Tipo tipo;

    private List<ContentDTO> content;

    private List<ContentDTO> contentPending;

    private LocalDateTime dataInizio;

    private LocalDateTime dataFine;

    private LocalTime[] openingTime;

    private LocalTime[] closingTime;



    public POIDTO(String id, String name, String description, Coordinate coordinate, Tipo tipo, List<ContentDTO> content, List<ContentDTO> contentPending,LocalTime[] openingTime, LocalTime[] closingTime,LocalDateTime oraInizio, LocalDateTime oraFine) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinate =  new Coordinate(coordinate.getLatitudine(),coordinate.getLongitudine());
        this.tipo = tipo;
        this.content=content;
        this.contentPending=contentPending;
        this.closingTime=null;
        this.openingTime=null;
        this.dataFine=null;
        this.dataInizio=null;
        if(tipo.equals(Tipo.EVENTO))
        {
            this.dataFine = oraFine;
            this.dataInizio = oraInizio;
            this.closingTime=null;
            this.openingTime=null;
        }
        else if(tipo.equals(Tipo.LUOGOCONORA))
        {
            this.openingTime=openingTime;
            this.closingTime=closingTime;

            this.dataFine=null;
            this.dataInizio=null;
        }

    }



    public String getId() {
        return id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public Coordinate getCoordinate() {
        return coordinate;
    }

    public List<ContentDTO> getContent() {
        return content;
    }

    public List<ContentDTO> getContentPending() {
        return contentPending;
    }

    public LocalDateTime getDataInizio() {
        return dataInizio;
    }

    public LocalDateTime getDataFine() {
        return dataFine;
    }

    public LocalTime[] getOpeningTime() {
        return openingTime;
    }

    public LocalTime[] getClosingTime() {
        return closingTime;
    }





}
