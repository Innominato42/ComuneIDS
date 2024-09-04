package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.DTO.ContentDTO;
import com.example.comuneids2024.Model.DTO.POIDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Document(collection = "POILuogo")
public class POILuogo extends POI {

    @Override
    public LocalDateTime getDataFine() {
        return null;
    }

    @Override
    public LocalDateTime getDataInizio() {
        return null;
    }

    @Override
    public LocalTime[] getOpeningTime() {
        return null;
    }

    @Override
    public LocalTime[] getClosingTime() {
        return null;
    }

    public POILuogo(Coordinate coordinate) {
        super(coordinate);
        this.setTipo(Tipo.LUOGO);
    }

    public POILuogo() {
        super();
    }

    @Override
    public void insertPOIInfo(String name, String description) {
        if ((name == null) || (description == null)) {
            throw new NullPointerException();
        }
        this.setName(name);
        this.setDescription(description);
    }

    @Override
    public POIDTO getPOIInfo() {
        List<ContentDTO> contents = this.getContents().stream().map(Content::getContentInfo).toList();
        List<ContentDTO> pendingContents = this.getContentsPending().stream().map(Content::getContentInfo).toList();
        return new POIDTO(this.getPOIId(), this.getName(), this.getDescription(), this.getCoord(), this.getTipo(), contents, pendingContents,null,null,null,null);
    }
}
