package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.GI.POIGI;
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
        this.setTipo(Tipo.EVENTO);
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

    public void addDate(LocalDateTime oraInizio, LocalDateTime oraFine)
    {
        this.oraFine=oraFine;
        this.oraInizio=oraInizio;
    }

    @Override
    public void setTipo(Tipo tipo)
    {
        super.setTipo(tipo);
    }

    @Override
    public void addContent(Content c)
    {
        super.addContent(c);
    }

    @Override
    public POIGI getPOI()
    {
        return new POIGI(this.getPOIID(),this.getName(),this.getDescription(),this.getCoordinate(),this.getTipo());
    }

}
