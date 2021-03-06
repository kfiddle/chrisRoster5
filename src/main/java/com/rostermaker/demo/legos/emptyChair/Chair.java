package com.rostermaker.demo.legos.emptyChair;

import com.rostermaker.demo.models.instrument.Instrument;
import com.rostermaker.demo.models.part.Part;
import com.rostermaker.demo.models.piece.Piece;
import com.rostermaker.demo.models.show.Show;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
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
    private List<Part> parts = new ArrayList<>();

    public Chair() {
    }

    public Chair(ChairBuilder chairBuilder) {
        this.piece = chairBuilder.piece;
        this.show = chairBuilder.show;
        this.parts = chairBuilder.parts;
        primaryPart = parts.get(0);
    }


    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
        this.primaryPart = parts.get(0);
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
        return primaryPart;
    }

    @Override
    public int compareTo(Chair next) {
        return primaryPart.compareTo(next.getPrimaryPart());
    }


}
