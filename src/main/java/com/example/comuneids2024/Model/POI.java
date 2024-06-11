package com.example.comuneids2024.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class POI {

    @Id
    private Long idPOI;
    private String name;
    private String description;
    private Tipo tipo;
    @Embedded
    private Coordinate coordinate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Content> content;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Content> contentPending;

    public POI(String name,String description,Coordinate coordinate,Tipo tipo)
    {
        this.name=name;
        this.content=new ArrayList<>();
        this.contentPending=new ArrayList<>();
        this.description=description;
        this.coordinate=coordinate;
        this.tipo=tipo;

    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Tipo getType() {
        return tipo;
    }

    public Coordinate getCoord() {
        return coordinate;
    }

    public  List<Content> getContents(){
        return this.content;
    };

    public List<Content> getContentsPending() {
        return contentPending;
    }

    public void addContent(Content c){
        if(c == null) throw new NullPointerException("Contenuto null");
        this.content.add(c);
    }

    public void addContentPending(Content c){
        if(c == null) throw new NullPointerException("Contenuto null");
        this.contentPending.add(c);
    }
    public void setName(String name) {
        this.name = name;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(Tipo tipo){
        this.tipo = tipo;
    }


    public Long getPOIId() {
        return idPOI;
    }

}
