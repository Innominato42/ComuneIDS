package com.example.comuneids2024.Model;


import com.example.comuneids2024.Model.DTO.POIDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "POI")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = POILuogo.class, name = "LUOGO"),
        @JsonSubTypes.Type(value = POIEvento.class, name = "EVENTO"),
        @JsonSubTypes.Type(value = POILuogoOra.class, name = "LUOGOCONORA")
})
public abstract class POI {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poi_generator")
    private String idPOI;
    private String name;
    private String description;
    private Tipo tipo;

    @Embedded
    private Coordinate coordinate;

    private List<Content> content;

    private List<Content> contentPending;

    public POI(String name, String description, Coordinate coordinate, Tipo tipo) {
        this.name = name;
        this.content = new ArrayList<>();
        this.contentPending = new ArrayList<>();
        this.description = description;
        this.coordinate = new Coordinate(coordinate.getLatitudine(), coordinate.getLongitudine());
        this.tipo = tipo;
    }

    public POI(Coordinate coordinate) {
        if (coordinate == null) {
            throw new NullPointerException("Coordinate null");
        }
        this.coordinate = coordinate;
        this.content = new ArrayList<>();
        this.contentPending = new ArrayList<>();
    }

    public POI() {
        this.content = new ArrayList<>();
        this.contentPending = new ArrayList<>();
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

    public List<Content> getContents() {
        return this.content;
    }

    public Content getContentIndex(int  id)
    {
        if(content.isEmpty())
        {
            return null;
        }
        return content.get(id);
    }

    public Content getContentById(String id)
    {
        return getContents().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

    }

    public List<Content> getContentsPending() {
        return contentPending;
    }

    public void addContent(Content c) {
        if (c == null) throw new NullPointerException("Contenuto null");
        this.content.add(c);
    }

    public void addContentPending(Content c) {
        if (c == null) throw new NullPointerException("Contenuto null");
        this.contentPending.add(c);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getPOIId() {
        return idPOI;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public abstract void insertPOIInfo(String name, String description);

    public abstract POIDTO getPOIInfo();
}
