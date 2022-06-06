package com.rostermaker.demo.models.show;


import com.example.demo.basicModels.DateTime;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Show implements Comparable<Show> {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @ElementCollection
    private List<DateTime> performanceDates = new ArrayList<>();

    @ElementCollection
    private List<DateTime> rehearsalDates = new ArrayList<>();

    private int numberOfServices;

    private String notes;

    public Show() {
    }

    public Show(com.example.demo.basicModels.show.ShowBuilder showBuilder) {
        this.title = showBuilder.title;
        this.performanceDates = showBuilder.performanceDates;
        this.rehearsalDates = showBuilder.rehearsalDates;
        this.numberOfServices = showBuilder.numberOfServices;
        this.notes = showBuilder.notes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPerformanceDates(List<DateTime> performanceDates) {
        this.performanceDates = performanceDates;
    }

    public void setRehearsalDates(List<DateTime> rehearsalDates) {
        this.rehearsalDates = rehearsalDates;
    }

    public void setNumberOfServices(int numberOfServices) {
        this.numberOfServices = numberOfServices;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<DateTime> getPerformanceDates() {
        return performanceDates;
    }

    public List<DateTime> getRehearsalDates() {
        return rehearsalDates;
    }

    public int getNumberOfServices() {
        return numberOfServices;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public int compareTo(Show next) {
        return performanceDates.get(0).getDate().compareTo(next.performanceDates.get(0).getDate());
    }

}
