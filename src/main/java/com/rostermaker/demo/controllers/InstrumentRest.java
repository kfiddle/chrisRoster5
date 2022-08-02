package com.rostermaker.demo.controllers;


import com.rostermaker.demo.models.instrument.Instrument;
import com.rostermaker.demo.repos.InstrumentRepo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class InstrumentRest {

    @Resource
    private InstrumentRepo instrumentRepo;


    @RequestMapping("/get-all-instruments")
    public List<Instrument> getAllInstruments() {
        List<Instrument> allInsts = (List<Instrument>) instrumentRepo.findAll();
        Collections.sort(allInsts);
        return allInsts;
    }

    @RequestMapping("/get-all-instruments-names")
    public List<String> getAllInstrumentNames() {
        List<Instrument> allInsts = (List<Instrument>) instrumentRepo.findAll();
        List<String> instNames = new ArrayList<>();

        Collections.sort(allInsts);
        for (Instrument instrument : allInsts) {
            instNames.add(instrument.getName());
        }
        return instNames;
    }

    @PostMapping("/add-instrument")
    public Instrument addAnInstrument(@RequestBody Instrument newInstrument) throws IOException {
        try {
            if (!instrumentRepo.existsByName(newInstrument.getName())) {
                Instrument instrumentToAdd = new Instrument(newInstrument.getName());
                if (newInstrument.getScoreOrder() > 0) {
                    instrumentToAdd.setScoreOrder(newInstrument.getScoreOrder());
                }
                instrumentRepo.save(instrumentToAdd);
                return newInstrument;
            }

        } catch (Exception error) {
            error.printStackTrace();
        }
        return null;
    }

    @PostMapping("/edit-abbreviation")
    public Instrument editInstAbbrev(@RequestBody Instrument incomingInstrument) throws IOException {
        try {
            Optional<Instrument> instToFind = instrumentRepo.findById(incomingInstrument.getId());
            if (instToFind.isPresent()) {
                Instrument inst = instToFind.get();
                inst.setAbbreviation(incomingInstrument.getAbbreviation());
                instrumentRepo.save(inst);
                return inst;
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return null;

    }


}
