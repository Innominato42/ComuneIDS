package com.example.comuneids2024.Model;

public class POIEventoFactory extends POIFactory{
    @Override
    public POI createPOI(Coordinate c) {
        return new POIEvento(c);
    }
}
