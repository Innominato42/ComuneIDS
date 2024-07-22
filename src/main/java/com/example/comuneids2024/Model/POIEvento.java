package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.DTO.POIDTO;
import jakarta.persistence.ManyToMany;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
//POI INTERVALLO
@Document(collection = "POIEvento")
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
    public String getPOIId()
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
    public void insertPOIInfo(String name, String description) {
        this.setName(name);
        this.setDescription(description);

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
    public POIDTO getPOIInfo()
    {
        List<Content> contents = this.getContents().stream().map(c -> c.getContentInfo()).toList();
        List<Content> pendingContentsGI = this.getContentsPending().stream().map(pc -> pc.getContentInfo()).toList();
        return new POIDTO(this.getPOIId(),this.getName(),this.getDescription(),this.getCoordinate(),this.getTipo(),contents,pendingContentsGI);
    }




}
