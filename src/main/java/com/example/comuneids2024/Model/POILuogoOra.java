package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.DTO.POIDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalTime;
import java.util.List;


    public class POILuogoOra extends POI {

        @Field("openingTime")
        private LocalTime[] openingTime= new LocalTime[7];

        @Field("closingTime")
    private LocalTime[] closingTime= new LocalTime[7];

    public POILuogoOra(Coordinate coordinate) {
        super(coordinate);
        this.setTipo(Tipo.LUOGOCONORA);
    }

    public void insertTime(LocalTime[] openingTime, LocalTime[] closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    @Override
    public void insertPOIInfo(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

    @Override
    public POIDTO getPOIInfo() {
        List<Content> contents = this.getContents().stream().map(Content::getContentInfo).toList();
        List<Content> pendingContents = this.getContentsPending().stream().map(Content::getContentInfo).toList();
        return new POIDTO(this.getPOIId(), this.getName(), this.getDescription(), this.getCoord(), this.getTipo(), contents, pendingContents,getOpeningTime(),getClosingTime(),null,null);
    }

    public LocalTime[] getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime[] openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime[] getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime[] closingTime) {
        this.closingTime = closingTime;
    }
}
