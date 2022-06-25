package com.rostermaker.demo.repos;

import com.rostermaker.demo.models.instrument.Instrument;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InstrumentRepo extends CrudRepository<Instrument, Long> {


    boolean existsByName(String name);
}
