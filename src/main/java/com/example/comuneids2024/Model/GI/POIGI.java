package com.example.comuneids2024.Model.GI;

import com.example.comuneids2024.Model.Coordinate;
import com.example.comuneids2024.Model.Tipo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

//TODO CAPIRE SE QUESTO TIPO DI CLASSE SERVE VERAMENTE
public class POIGI {
    private final Long id;
    private final String name;
    private final String description;
    private final Coordinate coordinate;
    private final Tipo type;

    private List<ContentGI> contentGI;

    private List<ContentGI> contentPendingGI;

    private LocalDateTime dataInizio;

    private LocalDateTime dataFine;

    private LocalTime[] openingTime;

    private LocalTime[] closingTime;

    public POIGI(Long id, String name, String description, Coordinate coordinate, Tipo type, List<ContentGI> contestGI, List<ContentGI> contentPendingGI) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinate = coordinate;
        this.type = type;
        this.closingTime=null;
        this.contentGI=contestGI;
        this.contentPendingGI=contentPendingGI;
        this.openingTime=null;
        this.dataFine=null;
        this.dataInizio=null;
    }

    public Long getId() {
        return id;
    }

    public Tipo getType() {
        return type;
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

    public List<ContentGI> getContentGI() {
        return contentGI;
    }

    public List<ContentGI> getContentPendingGI() {
        return contentPendingGI;
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
                "\n Type=" + type;
    }

}
