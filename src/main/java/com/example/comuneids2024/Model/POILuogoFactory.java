package com.example.comuneids2024.Model;

public class POILuogoFactory extends POIFactory{
    @Override
    public POI createPOI(Coordinate c) {
        return new POILuogo(c);
    }
}
