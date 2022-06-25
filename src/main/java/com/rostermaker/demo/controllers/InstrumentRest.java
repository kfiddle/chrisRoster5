package com.rostermaker.demo.controllers;


import com.rostermaker.demo.models.instrument.Instrument;
import com.rostermaker.demo.repos.InstrumentRepo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @PostMapping("add-instrument")
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


}
