package com.rostermaker.demo.models.part;


import com.rostermaker.demo.models.instrument.Instrument;

import javax.persistence.*;

@Embeddable
public class Part implements Comparable<Part> {

    @ManyToOne
    private Instrument instrument;
    private int rank;

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public int getRank() {
        return rank;
    }

    public boolean isPrincipalHorn() {
        return instrument.getName().equals("HORN") && rank == 1;
    }

    @Override
    public int compareTo(Part next) {
        if (instrument.compareTo(next.getInstrument()) != 0) {
            return instrument.compareTo(next.getInstrument());
        } else return Integer.compare(rank, next.getRank());
    }
}

//    public int compareTo(Chair next) {
//        if (primaryPart.compare(next.getPrimaryPart()) != 0) {
//            return primaryPart.compare(next.getPrimaryPart());
//        } else if (rank > next.getRank()) {
//            return 1;
//        } else if (rank < next.getRank()) {
//            return -1;
//        } else {
//            return 0;
//        }
//    }
