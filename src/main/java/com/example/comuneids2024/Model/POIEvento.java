package com.example.comuneids2024.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class POIEvento extends POI{

    private LocalDateTime oraInizio;

    private LocalDateTime oraFine;

    public POIEvento(Coordinate coordinate)
    {
        super(coordinate);
        this.setType(Tipo.EVENTO);
    }

    public POIEvento(){
        super();
    }
    @Override
    public Long getPOIID()
    {
        return super.getPOIId();
    }
    @Override
    public String getName()
    {
        return super.getName();
    }

    @Override
    public String getDescription()
    {
        return super.getDescription();
    }
    @Override
    public Tipo getTipo()
    {
        return super.getTipo();
    }
    @Override
    public Coordinate getCoordinate()
    {
        return super.getCoord();
    }

    @Override
    @ManyToMany
    public List<Content> getContents()
    {
        return super.getContents();
    }
    public LocalDateTime getOraInizio() {
        return oraInizio;
    }

    public LocalDateTime getOraFine()
    {
        return oraFine;
    }

    //TODO finire get e set
}
