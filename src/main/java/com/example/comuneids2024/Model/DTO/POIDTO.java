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

    public POIDTO(String id, String name, String description, Coordinate coordinate, Tipo tipo, List<Content> contest, List<Content> contentPending) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinate = coordinate;
        this.tipo = tipo;
        this.closingTime=null;
        this.content=contest;
        this.contentPending=contentPending;
        this.openingTime=null;
        this.dataFine=null;
        this.dataInizio=null;
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
