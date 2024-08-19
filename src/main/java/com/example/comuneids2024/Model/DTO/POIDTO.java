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

    private List<Content> content;

    private List<Content> contentPending;

    private LocalDateTime dataInizio;

    private LocalDateTime dataFine;

    private LocalTime[] openingTime;

    private LocalTime[] closingTime;

    // costruttore POILUOGO

    public POIDTO(String id, String name, String description, Coordinate coordinate, Tipo tipo, List<Content> content, List<Content> contentPending,LocalTime[] openingTime, LocalTime[] closingTime,LocalDateTime oraInizio, LocalDateTime oraFine) {
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
   /* public POIDTO(String id, String name, String description,Coordinate coordinate, Tipo tipo, List<Content> content, List<Content> contentPending,LocalTime[] openingTime, LocalTime[] closingTime)
    {

        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinate =  new Coordinate(coordinate.getLatitudine(),coordinate.getLongitudine());
        this.tipo = tipo;
        this.closingTime=closingTime;
        this.content=content;
        this.contentPending=contentPending;
        this.openingTime=openingTime;
        this.dataFine=null;
        this.dataInizio=null;
    }

    public POIDTO(String id, String name, String description,Coordinate coordinate, Tipo tipo, List<Content> content, List<Content> contentPending,LocalDateTime oraInizio, LocalDateTime oraFine)
    {

        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinate =  new Coordinate(coordinate.getLatitudine(),coordinate.getLongitudine());
        this.tipo = tipo;
        this.closingTime=null;
        this.content=content;
        this.contentPending=contentPending;
        this.openingTime=null;
        this.dataFine=oraFine;
        this.dataInizio=oraInizio;
    }*/

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

    public Coordinate getCoordinates() {
        return coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public List<Content> getContent() {
        return content;
    }

    public List<Content> getContentPending() {
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

    public LocalDateTime getDateOpen()
    {
        return dataInizio;
    }
    public LocalDateTime getDateClose()
    {
        return dataFine;
    }

    @Override
    public String toString() {
        return  "Id: "+this.id+" Name= " + name +
                "\nDescription=" + description +
                "\n Coordinates= lon " + coordinate.getLatitudine() + " lon " +coordinate.getLongitudine()+
                "\n Type=" + tipo;
    }

}
