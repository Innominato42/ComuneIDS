package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.GI.ContentGI;
import com.example.comuneids2024.Model.GI.POIGI;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class POILuogo extends POI{

    public POILuogo(Coordinate coordinate)
    {
        super(coordinate);
        this.setTipo(Tipo.LUOGO);
    }
    public POILuogo()
    {
        super();
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
    @ManyToMany
    public List<Content> getContents() {
        return super.getContents();
    }

    @Override
    @ManyToMany
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
    public Coordinate getCoordinate() {
        return super.getCoordinate();
    }

    @Override
    public Long getPOIId() {
        return super.getPOIId();
    }

    @Override
    public void insertPOIInfo(String name, String description) {
        if((name==null)||(description==null))
        {
            throw new NullPointerException();
        }
        this.setName(name);
        this.setDescription(description);
    }


    @Override
    public POIGI getPOIInfo()
    {
        List<Content> contents = this.getContents().stream().map(c -> c.getContentInfo()).toList();
        List<Content> pendingContents = this.getContentsPending().stream().map(pc -> pc.getContentInfo()).toList();
        return new POIGI(this.getPOIId(),this.getName(),this.getDescription(),this.getCoordinate(),this.getTipo(),contents,pendingContents);
    }

}
