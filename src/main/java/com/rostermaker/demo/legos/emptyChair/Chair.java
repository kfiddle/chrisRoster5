package com.rostermaker.demo.legos.emptyChair;

import com.rostermaker.demo.models.part.Part;
import com.rostermaker.demo.models.piece.Piece;
import com.rostermaker.demo.models.show.Show;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Chair implements Comparable<Chair> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Piece piece;

    @ManyToOne
    private Show show;

    @Embedded
    private Part primaryPart;

    @ElementCollection
    private List<Part> parts;

    private String specialDesignate;


    public Chair() {
    }

//    public Chair(ChairBuilder chairBuilder) {
//        this.piece = chairBuilder.piece;
//        this.show = chairBuilder.show;
//        this.rank = chairBuilder.rank;
//        this.parts = chairBuilder.parts;
//        this.primaryPart = parts.get(0);
//        this.specialDesignate = chairBuilder.specialDesignate;
//    }


    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
        primaryPart = parts.get(0);
    }

    public void setPrimaryPart(Part primaryPart) {
        this.primaryPart = primaryPart;
    }

    public void setSpecialDesignate(String specialDesignate) {
        this.specialDesignate = specialDesignate;
    }

    public Long getId() {
        return id;
    }

    public Piece getPiece() {
        return piece;
    }

    public Show getShow() {
        return show;
    }

    public List<Part> getParts() {
        return parts;
    }

    public Part getPrimaryPart() {
        return parts.get(0);
    }

    public String getSpecialDesignate() {
        return specialDesignate;
    }

    public boolean hasAssDesignate() {
        return specialDesignate != null && specialDesignate.equals("Assist");
    }


    @Override
    public int compareTo(Chair next) {
        return primaryPart.compareTo(next.getPrimaryPart());
    }
}
