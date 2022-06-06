package com.rostermaker.demo.models.show;


import com.example.demo.enums.Event;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Horloge implements Comparable<Horloge> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Show show;

    private Event event;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public Horloge() {
    }


    public Horloge(HorlogeBuilder horlogeBuilder) {
        this.show = horlogeBuilder.show;
        this.event = horlogeBuilder.event;
        this.date = horlogeBuilder.date;
        this.startTime = horlogeBuilder.startTime;
        this.endTime = horlogeBuilder.endTime;
    }


    public void setShow(Show show) {
        this.show = show;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public Show getShow() {
        return show;
    }

    public Event getEvent() {
        return event;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public int compareTo(Horloge other) {
        if (date.isAfter(other.getDate())) {
            return 1;
        } else if (date.isBefore(other.getDate())) {
            return -1;
        } else {
            if (this.startTime != null && other.getStartTime() != null) {
                return this.startTime.compareTo(other.startTime);
            } else {
                return 0;
            }
        }
    }
}
