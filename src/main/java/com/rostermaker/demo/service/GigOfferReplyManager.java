package com.rostermaker.demo.service;

import com.rostermaker.demo.enums.Reply;
import com.rostermaker.demo.legos.ShowPiece;
import com.rostermaker.demo.legos.playerInChair.PIC;
import com.rostermaker.demo.models.gigOffer.GigOffer;
import com.rostermaker.demo.models.instrument.Instrument;
import com.rostermaker.demo.models.logEvent.LogEvent;
import com.rostermaker.demo.models.player.Player;
import com.rostermaker.demo.repos.GigOfferRepo;
import com.rostermaker.demo.repos.LogEventRepo;
import com.rostermaker.demo.repos.PICRepo;
import com.rostermaker.demo.repos.ShowPieceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class GigOfferReplyManager {

    private final GigOfferRepo gigOfferRepo;
    private final LogEventRepo logEventRepo;
    private final PICRepo picRepo;
    private final ShowPieceRepo showPieceRepo;

    @Autowired
    public GigOfferReplyManager(GigOfferRepo gigOfferRepo, LogEventRepo logEventRepo, PICRepo picRepo, ShowPieceRepo showPieceRepo) {
        this.gigOfferRepo = gigOfferRepo;
        this.logEventRepo = logEventRepo;
        this.picRepo = picRepo;
        this.showPieceRepo = showPieceRepo;
    }

    private void fillChair(GigOffer offerToSetReply) {
        Player playerToSit = offerToSetReply.getPlayer();
        int playerRank = playerToSit.getRank();
        Instrument playerPrimInst = playerToSit.getPrimaryInstrument();

        System.out.println(playerToSit.getFirstNameArea());

        //for pops only

        Collection<PIC> picsToFill = picRepo.findAllByShow(offerToSetReply.getShow());
        for (PIC pic : picsToFill) {
            if (pic.getPrimaryPart().getRank() == playerRank && pic.getPrimaryPart().getInstrument().equals(playerPrimInst)) {
                pic.setPlayer(playerToSit);
                picRepo.save(pic);
            }
        }

        //for syms

        for (ShowPiece showPiece : showPieceRepo.findAllByShow(offerToSetReply.getShow())) {
            for (PIC pic : picRepo.findAllByShowPiece(showPiece)) {
                if (pic.getPrimaryPart().getRank() == playerRank && pic.getPrimaryPart().getInstrument().equals(playerPrimInst)) {
                    pic.setPlayer(playerToSit);
                    picRepo.save(pic);
                }
            }
        }
    }



    public GigOffer saveAndFillChairs(GigOffer incomingReply) {

        LocalDate currentDate = LocalDate.now();
            incomingReply.setReply(incomingReply.getReply());
            incomingReply.setResponseDate(currentDate);
            gigOfferRepo.save(incomingReply);

            if (incomingReply.getReply().equals(Reply.ACCEPT)) {
                fillChair(incomingReply);
            }

            LogEvent newEvent = new LogEvent(incomingReply, currentDate);
            logEventRepo.save(newEvent);

            return incomingReply;
    }

}
