package com.rostermaker.demo.models.instrument;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Instrument implements Comparable<Instrument> {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int scoreOrder;

    public Instrument() {
    }

    public Instrument(String name) {
        this.name = name;
    }

    public Instrument(String name, int scoreOrder) {
        this.name = name;
        this.scoreOrder = scoreOrder;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScoreOrder(int scoreOrder) {
        this.scoreOrder = scoreOrder;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScoreOrder() {
        return scoreOrder;
    }

    @Override
    public int compareTo(Instrument next) {
        return Integer.compare(this.scoreOrder, next.getScoreOrder());
    }

}
