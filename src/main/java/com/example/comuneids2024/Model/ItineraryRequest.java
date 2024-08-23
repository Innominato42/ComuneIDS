package com.example.comuneids2024.Model;

public class ItineraryRequest {

    private Itinerary itinerary;
    private String[] poi;


    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public String[] getPoi() {
        return poi;
    }

    public void setPoi(String[] poi) {
        this.poi = poi;
    }
}
