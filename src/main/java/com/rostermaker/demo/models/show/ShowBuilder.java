package com.rostermaker.demo.models.show;

import com.rostermaker.demo.models.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShowBuilder {

    public String title;

    public List<DateTime> performanceDates = new ArrayList<>();

    public List<DateTime> rehearsalDates = new ArrayList<>();

    public int numberOfServices;

    public String notes;

    public ShowBuilder() {
    }

    public ShowBuilder title(String title) {
        Optional<String> titleCheck = Optional.ofNullable(title);
        titleCheck.ifPresent(gottenTitle -> this.title = gottenTitle);
        return this;
    }

    public ShowBuilder withDate(DateTime primaryDate) {
        if (primaryDate != null) {
            performanceDates.add(primaryDate);
        }
        return this;
    }

    public ShowBuilder performanceDates(List<DateTime> performanceDates) {
        Optional<List<DateTime>> datesOpt = Optional.ofNullable(performanceDates);
        datesOpt.ifPresent(gotten -> this.performanceDates = gotten);
        return this;
    }

    public ShowBuilder rehearsalDates(List<DateTime> rehearsalDates) {
        Optional<List<DateTime>> datesOpt = Optional.ofNullable(rehearsalDates);
        datesOpt.ifPresent(gotten -> this.rehearsalDates = gotten);
        return this;
    }

    public ShowBuilder numberOfServices(int numberOfServices) {
        if (numberOfServices > 0) {
            this.numberOfServices = numberOfServices;
        }
        return this;
    }

    public ShowBuilder notes(String notes) {
        Optional<String> notesOpt = Optional.ofNullable(notes);
        notesOpt.ifPresent(gotten -> this.notes = gotten);
        return this;
    }


    public Show build() {
        return new Show(this);
    }
}
