package com.rostermaker.demo.legos.emptyChair;

import com.rostermaker.demo.models.piece.Piece;
import com.rostermaker.demo.models.show.Show;
import com.rostermaker.demo.enums.Part;

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

    private int rank;

    private String specialDesignate;

    @ElementCollection
    private List<Part> parts = new ArrayList<>();

    private Part primaryPart;

    public Chair() {
    }

    public Chair(ChairBuilder chairBuilder) {
        this.piece = chairBuilder.piece;
        this.show = chairBuilder.show;
        this.rank = chairBuilder.rank;
        this.parts = chairBuilder.parts;
        this.primaryPart = parts.get(0);
        this.specialDesignate = chairBuilder.specialDesignate;
    }


    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public void setRank(int rank) {
        this.rank = rank;
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

    public int getRank() {
        return rank;
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

    public boolean isPrincipalHorn() {
        return primaryPart.equals(Part.HORN) && rank == 1;
    }

    @Override
    public int compareTo(Chair next) {
        if (primaryPart.compare(next.getPrimaryPart()) != 0) {
            return primaryPart.compare(next.getPrimaryPart());
        } else if (rank > next.getRank()) {
            return 1;
        } else if (rank < next.getRank()) {
            return -1;
        } else {
            return 0;
        }
    }
}
