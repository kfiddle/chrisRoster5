package com.rostermaker.demo.legos.emptyChair;


import com.example.demo.basicModels.piece.Piece;
import com.example.demo.basicModels.show.Show;
import com.example.demo.enums.Part;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChairBuilder {

    public Piece piece;
    public Show show;

    public List<Part> parts = new ArrayList<>();

    public int rank;
    public String specialDesignate;

    public ChairBuilder() {
    }

    public ChairBuilder piece(Piece piece) {
        Optional<Piece> pieceOpt = Optional.ofNullable(piece);
        pieceOpt.ifPresent(gotten -> this.piece = gotten);
        return this;
    }

    public ChairBuilder show(Show show) {
        Optional<Show> showOpt = Optional.ofNullable(show);
        showOpt.ifPresent(gotten -> this.show = show);
        return this;
    }

    public ChairBuilder rank(int rank) {
        if (rank > 0) {
            this.rank = rank;
        }
        return this;
    }

    public ChairBuilder parts(List<Part> parts) {
        Optional<List<Part>> partsOpt = Optional.ofNullable(parts);
        partsOpt.ifPresent(gotten -> this.parts = gotten);
        return this;
    }


    public ChairBuilder primaryPart(Part part) {
        Optional<Part> partOpt = Optional.ofNullable(part);
        if (partOpt.isPresent() && parts.size() == 0) {
            parts.add(part);
        }
        return this;
    }

    public ChairBuilder specialDesignate(String specialDesignate) {
        Optional<String> specialOpt = Optional.ofNullable(specialDesignate);
        specialOpt.ifPresent(gotten -> this.specialDesignate = gotten);
        return this;
    }

    public com.example.demo.legos.emptyChair.Chair build() {
        return new com.example.demo.legos.emptyChair.Chair(this);
    }


}
