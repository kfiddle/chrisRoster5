package com.rostermaker.demo.controllers;


import com.rostermaker.demo.legos.ShowPiece;
import com.rostermaker.demo.legos.playerInChair.HornChairSorter;
import com.rostermaker.demo.legos.playerInChair.HornPICSorter;
import com.rostermaker.demo.legos.playerInChair.PIC;
import com.rostermaker.demo.legos.playerInChair.PlayerInChair;
import com.rostermaker.demo.models.part.Part;
import com.rostermaker.demo.repos.PICRepo;
import com.rostermaker.demo.repos.ShowPieceRepo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class PICRest {

    @Resource
    PICRepo picRepo;

    @Resource
    ShowPieceRepo showPieceRepo;


    @RequestMapping("/get-all-new-pics")
    public Collection<PIC> getAllPics() {
        return (Collection<PIC>) picRepo.findAll();
    }

    @RequestMapping("/get-better-pics-in-show-piece")
    public Collection<PIC> getAllPICSInAPieceOnShow(@RequestBody ShowPiece incomingShowPiece) {
        Optional<ShowPiece> showPieceToFind = showPieceRepo.findById(incomingShowPiece.getId());
        if (showPieceToFind.isPresent()) {
            List<PIC> picsToReturn = (List<PIC>) picRepo.findAllByShowPiece(showPieceToFind.get());
            Collections.sort(picsToReturn);
            HornPICSorter sorter = new HornPICSorter(picsToReturn);
            List<PIC> sortedPics = sorter.sort();

            for (PIC pic : sortedPics) {
                Part part = pic.getPrimaryPart();
                String instName = part.getInstrument().getName();
                int instRank = part.getRank();
                String assist = part.getSpecialDesignate();
            }

            return sorter.sort();
        }
        return null;
    }
}
