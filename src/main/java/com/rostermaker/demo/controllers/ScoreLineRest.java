package com.rostermaker.demo.controllers;


import com.rostermaker.demo.legos.ShowPiece;
import com.rostermaker.demo.legos.emptyChair.Chair;
import com.rostermaker.demo.legos.emptyChair.ChairBuilder;
import com.rostermaker.demo.legos.playerInChair.PICBuilder;
import com.rostermaker.demo.legos.playerInChair.PlayerInChair;
import com.rostermaker.demo.legos.scoreline.ScoreLine;
import com.rostermaker.demo.legos.scoreline.ScoreLineBuilder;
import com.rostermaker.demo.models.instrument.Instrument;
import com.rostermaker.demo.models.part.Part;
import com.rostermaker.demo.models.piece.Piece;
import com.rostermaker.demo.models.show.Show;
import com.rostermaker.demo.repos.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin
public class ScoreLineRest {

    @Resource
    ScoreLineRepo scoreLineRepo;

    @Resource
    PieceRepo pieceRepo;

    @Resource
    ShowRepo showRepo;

    @Resource
    ShowPieceRepo showPieceRepo;

    @Resource
    PICRepo picRepo;

    @Resource
    InstrumentRepo instrumentRepo;

    @Resource
    PlayerRepo playerRepo;

    public ScoreLine addScoreLine(ScoreLine incomingScoreLine) {
        Piece piece = null;
        Show show = null;
        ScoreLine scoreLineToSave;

        if (incomingScoreLine.getPiece() != null) {
            Optional<Piece> pieceToFind = pieceRepo.findById(incomingScoreLine.getPiece().getId());
            if (pieceToFind.isPresent()) {
                piece = pieceToFind.get();
            }
        } else if (incomingScoreLine.getShow() != null) {
            Optional<Show> showToFind = showRepo.findById(incomingScoreLine.getShow().getId());
            if (showToFind.isPresent()) {
                show = showToFind.get();
            }
        }

        List<Part> partsInNewScoreLine = new ArrayList<>();

        for (Part part : incomingScoreLine.getParts()) {
            Instrument inst;
            if (part.getInstrument().getName() != null) {
                inst = instrumentRepo.findByName(part.getInstrument().getName());
            } else {
                inst = instrumentRepo.findByAbbreviation(part.getInstrument().getAbbreviation());
            }

            Part partToAdd = new Part(inst);
            if (part.getRank() > 0) {
                partToAdd.setRank(part.getRank());
            } else if (part.getSpecialDesignate() != null) {
                partToAdd.setSpecialDesignate(part.getSpecialDesignate());
            }

            partsInNewScoreLine.add(partToAdd);
        }

        scoreLineToSave = new ScoreLineBuilder()
                .parts(partsInNewScoreLine)
                .piece(piece)
                .show(show)
                .build();
        scoreLineRepo.save(scoreLineToSave);

        if (scoreLineToSave.getShow() != null) {
            picRepo.save(new PICBuilder().show(show).fromScoreLine(scoreLineToSave).build());
        }

        if (piece != null && showPieceRepo.existsByPiece(piece)) {
            for (ShowPiece showPiece : showPieceRepo.findAllByPiece(piece)) {
                picRepo.save(new PICBuilder().showPiece(showPiece).fromScoreLine(scoreLineToSave).build());
            }
        }
        return scoreLineToSave;
    }

    @PostMapping("/add-scoreline")
    public ScoreLine addChairWrapper(@RequestBody ScoreLine incomingScoreLine) throws IOException {
        try {
            return addScoreLine(incomingScoreLine);
        } catch (Exception error) {
            error.printStackTrace();
        }
        return null;
    }

    @PostMapping("/add-scorelines")
    public Collection<ScoreLine> addScoreLinesCollection(@RequestBody Collection<ScoreLine> incomingScoreLines) throws IOException {
        Collection<ScoreLine> scoreLinesToReturn = new ArrayList<>();

        try {
            for (ScoreLine scoreLine : incomingScoreLines) {
                scoreLinesToReturn.add(addScoreLine(scoreLine));
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return scoreLinesToReturn;
    }

    @PostMapping("/get-scorelines-in-piece")
    public Collection<ScoreLine> getOrchestrationOfPiece(@RequestBody Piece incomingPiece) throws IOException {
        Optional<Piece> pieceCheck = pieceRepo.findById(incomingPiece.getId());

        try {
            if (pieceCheck.isPresent()) {
                List<ScoreLine> scoreLinesToReturn = (List<ScoreLine>) scoreLineRepo.findAllByPiece(pieceCheck.get());
                Collections.sort(scoreLinesToReturn);
                return scoreLinesToReturn;
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return null;
    }


}
