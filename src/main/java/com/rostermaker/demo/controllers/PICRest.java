package com.rostermaker.demo.controllers;


import com.rostermaker.demo.legos.ShowPiece;
import com.rostermaker.demo.legos.playerInChair.HornChairSorter;
import com.rostermaker.demo.legos.playerInChair.HornPICSorter;
import com.rostermaker.demo.legos.playerInChair.PIC;
import com.rostermaker.demo.legos.playerInChair.PlayerInChair;
import com.rostermaker.demo.models.part.Part;
import com.rostermaker.demo.models.player.Player;
import com.rostermaker.demo.models.show.Show;
import com.rostermaker.demo.repos.PICRepo;
import com.rostermaker.demo.repos.PlayerRepo;
import com.rostermaker.demo.repos.ShowPieceRepo;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    PlayerRepo playerRepo;


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

    @PostMapping("/put-player-in-pic/{picId}")
    public Optional<PIC> putAPlayerInAChair(@RequestBody Player incomingPlayer, @PathVariable Long
            picId) {

        try {
            Optional<PIC> premadePIC = picRepo.findById(picId);
            Optional<Player> playerToFind = playerRepo.findById(incomingPlayer.getId());
            if (premadePIC.isPresent() && playerToFind.isPresent()) {
                PIC pic = premadePIC.get();
                Player foundPlayer = playerToFind.get();
                boolean flagTest = false;

                ShowPiece possibleShowPiece;
                Show possibleShow;

                if (pic.getShow() == null) {
                    possibleShowPiece = pic.getShowPiece();
                    for (PIC pic1 : picRepo.findAllByShowPiece(possibleShowPiece)) {
                        if (pic1.hasThisPlayer(foundPlayer)) {
                            flagTest = true;
                        }
                    }
                } else {
                    possibleShow = pic.getShow();
                    for (PIC pic2 : picRepo.findAllByShow(possibleShow)) {
                        if (pic2.hasThisPlayer(foundPlayer)) {
                            flagTest = true;
                        }
                    }
                }

                if (!flagTest) {
                    pic.setPlayer(foundPlayer);
                    picRepo.save(pic);
                    return premadePIC;
                }

            }
        } catch (
                Exception error) {
            error.printStackTrace();

        }
        return Optional.empty();
    }

}
