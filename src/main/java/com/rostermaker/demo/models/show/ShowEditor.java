package com.rostermaker.demo.models.show;

import com.example.demo.basicModels.DateTime;

import java.util.List;
import java.util.Optional;

public class ShowEditor {

    Show storedShow;

    public ShowEditor(Show storedShow) {
        this.storedShow = storedShow;
    }

    public void editFrom(Show incoming) {
        Optional<String> titleOpt = Optional.ofNullable(incoming.getTitle());
        Optional<List<DateTime>> performanceDatesOpt = Optional.ofNullable(incoming.getPerformanceDates());
        Optional<List<DateTime>> rehearsalDatesOpt = Optional.ofNullable(incoming.getRehearsalDates());
        int services = incoming.getNumberOfServices();
        Optional<String> notesOpt = Optional.ofNullable(incoming.getNotes());

        titleOpt.ifPresent(gotten -> storedShow.setTitle(gotten));
        performanceDatesOpt.ifPresent(gotten -> storedShow.setPerformanceDates(gotten));
        rehearsalDatesOpt.ifPresent(gotten -> storedShow.setRehearsalDates(gotten));
        if (services > 0) {
            storedShow.setNumberOfServices(services);
        }
        notesOpt.ifPresent(gotten -> storedShow.setNotes(gotten));
    }
}
