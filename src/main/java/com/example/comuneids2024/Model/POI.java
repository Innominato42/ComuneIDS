package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.GI.POIGI;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class POI {

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

    public POI(Coordinate coordinate)
    {
        if(coordinate==null)
        {
            throw new NullPointerException("Coordinate null");
        }
        this.coordinate=coordinate;
        this.content= new ArrayList<>();
        this.contentPending=new ArrayList<>();
    }

    public POI()
    {
        this.content=new ArrayList<>();
        this.contentPending=new ArrayList<>();
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Tipo getTipo() {
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

    public void setTipo(Tipo tipo){
        this.tipo = tipo;
    }


    public Long getPOIId() {
        return idPOI;
    }


    public  Coordinate getCoordinate()
    {
        return coordinate;
    }

    public abstract void insertPOIInfo(String name, String description);


    public abstract POIGI getPOIInfo();



}
