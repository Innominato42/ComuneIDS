package com.example.comuneids2024.Model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Coordinate {

    private double latitudine;

    private double longitudine;

    public Coordinate(double latitudine, double longitudine)
    {
        this.latitudine=latitudine;
        this.longitudine=longitudine;
    }


    public Coordinate() {

    }

    public double getLatitudine()
    {
        return this.latitudine;
    }

    public double getLongitudine()
    {
        return this.longitudine;
    }
}
