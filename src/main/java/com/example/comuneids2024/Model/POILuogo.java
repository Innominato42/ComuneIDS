package com.example.comuneids2024.Model;

import com.example.comuneids2024.Model.DTO.POIDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "POILuogo")
public class POILuogo extends POI {

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
        List<Content> contents = this.getContents().stream().map(c -> c.getContentInfo()).toList();
        List<Content> pendingContents = this.getContentsPending().stream().map(pc -> pc.getContentInfo()).toList();
        return new POIDTO(this.getPOIId(), this.getName(), this.getDescription(), this.getCoord(), this.getTipo(), contents, pendingContents,null,null,null,null);
    }
}
