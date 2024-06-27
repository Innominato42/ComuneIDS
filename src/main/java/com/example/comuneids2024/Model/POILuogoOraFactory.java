package com.example.comuneids2024.Model;



public class POILuogoOraFactory extends POIFactory{
    @Override
    public POI createPOI(Coordinate c) {
        return new POILuogoOra(c);
    }
}
