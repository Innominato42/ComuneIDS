package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.GI.ContentGI;
import com.example.comuneids2024.Model.GI.POIGI;
import org.bson.codecs.jsr310.LocalTimeCodec;

import java.time.LocalTime;
import java.util.List;

public class POILuogoOra extends POI{

    private LocalTime[] openingTime;

    private LocalTime[] closingTime;

    public POILuogoOra(Coordinate coordinate)
    {
        super(coordinate);
        this.setTipo(Tipo.LUOGOCONORA);
        openingTime=new LocalTime[7];
        closingTime=new LocalTime[7];
    }

    public void insertTime(LocalTime[] openingTime, LocalTime[] closingTime)
    {
        this.openingTime=openingTime;
        this.closingTime=closingTime;
    }
    @Override
    public void insertPOIInfo(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public Tipo getTipo() {
        return super.getTipo();
    }

    @Override
    public Coordinate getCoord() {
        return super.getCoord();
    }

    @Override
    public List<Content> getContents() {
        return super.getContents();
    }

    @Override
    public List<Content> getContentsPending() {
        return super.getContentsPending();
    }

    @Override
    public void addContent(Content c) {
        super.addContent(c);
    }

    @Override
    public void addContentPending(Content c) {
        super.addContentPending(c);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @Override
    public void setTipo(Tipo tipo) {
        super.setTipo(tipo);
    }

    @Override
    public Long getPOIId() {
        return super.getPOIId();
    }

    @Override
    public Coordinate getCoordinate() {
        return super.getCoordinate();
    }

    @Override
    public POIGI getPOIInfo() {
        List<ContentGI> contentsGI = this.getContents().stream().map(c -> c.getContentInfo()).toList();
        List<ContentGI> pendingContentsGI = this.getContentsPending().stream().map(pc -> pc.getContentInfo()).toList();
        return new POIGI(this.getPOIId(),this.getName(),this.getDescription(),this.getCoordinate(),this.getTipo(),contentsGI,pendingContentsGI);
    }

    public LocalTime[] getOpeningTime(){return openingTime;}

    public LocalTime[] getClosingTime(){return closingTime;}
}
