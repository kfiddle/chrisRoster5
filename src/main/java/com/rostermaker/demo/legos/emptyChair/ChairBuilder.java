package com.rostermaker.demo.legos.emptyChair;


import com.rostermaker.demo.models.part.Part;
import com.rostermaker.demo.models.piece.Piece;
import com.rostermaker.demo.models.show.Show;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChairBuilder {

    public Piece piece;
    public Show show;


    public List<Part> parts = new ArrayList<>();

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

    public ChairBuilder part(Part part) {
        Optional<Part> partOpt = Optional.ofNullable(part);
        partOpt.ifPresent(gotten -> this.parts.add(gotten));
        return this;
    }

    public ChairBuilder parts(List<Part> parts) {
        Optional<List<Part>> partsOpt = Optional.ofNullable(parts);
        partsOpt.ifPresent(partList -> this.parts = partList);
        return this;
    }

    public Chair build() {
        return new Chair(this);
    }

}
