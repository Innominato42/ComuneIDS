package com.example.comuneids2024.Model.GI;

import com.example.comuneids2024.Model.Coordinate;
import com.example.comuneids2024.Model.Tipo;

public class POIGI {
    private final Long id;
    private final String name;
    private final String description;
    private final Coordinate coordinate;
    private final Tipo type;

    public POIGI(Long id, String name, String description, Coordinate coordinate, Tipo type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinate = coordinate;
        this.type = type;
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


    @Override
    public String toString() {
        return  "Id: "+this.id+" Name= " + name +
                "\nDescription=" + description +
                "\n Coordinates= lon " + coordinate.getLatitudine() + " lon " +coordinate.getLongitudine()+
                "\n Type=" + type;
    }

}
