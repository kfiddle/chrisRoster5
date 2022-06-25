package com.rostermaker.demo.controllers;

import com.rostermaker.demo.legos.ShowPiece;
import com.rostermaker.demo.legos.emptyChair.Chair;
import com.rostermaker.demo.legos.playerInChair.PlayerInChair;
import com.rostermaker.demo.models.piece.Piece;
import com.rostermaker.demo.models.show.Show;
import com.rostermaker.demo.repos.ChairRepo;
import com.rostermaker.demo.repos.PlayerInChairRepo;
import com.rostermaker.demo.repos.ShowPieceRepo;
import com.rostermaker.demo.repos.ShowRepo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@CrossOrigin
@RestController
public class ShowPieceRest {

    @Resource
    ShowPieceRepo showPieceRepo;

    @Resource
    ShowRepo showRepo;

    @Resource
    ChairRepo chairRepo;

    @Resource
    PlayerInChairRepo picRepo;

    @RequestMapping("/get-all-show-pieces")
    public Collection<ShowPiece> getAllShowPieces() {
        return (Collection<ShowPiece>) showPieceRepo.findAll();
    }

    public ShowPiece addAShowPiece(ShowPiece showPieceToAdd) {
        ShowPiece newShowPiece = new ShowPiece(showPieceToAdd.getPiece(), showPieceToAdd.getShow(), showPieceToAdd.getOrderNum());
        showPieceRepo.save(newShowPiece);


        if (chairRepo.existsByPiece(newShowPiece.getPiece())) {
            for (Chair chair : chairRepo.findAllByPiece(newShowPiece.getPiece())) {
                picRepo.save(new PlayerInChair(newShowPiece, chair));
            }
        }
        return newShowPiece;
    }

    @PostMapping("/add-show-piece")
    public ShowPiece addSingleShowPiece(@RequestBody ShowPiece showPieceToAdd) throws IOException {

        try {
            return addAShowPiece(showPieceToAdd);
        } catch (
                Exception error) {
            error.printStackTrace();
        }
        return null;
    }

    @PostMapping("/edit-showpiece-ordernum/{orderNum}")
    public Optional<ShowPiece> editWhereOnProgram(@PathVariable int orderNum, @RequestBody ShowPiece incoming) throws IOException {
        Optional<ShowPiece> showPieceToFind = showPieceRepo.findById(incoming.getId());
        if (showPieceToFind.isPresent()) {
            ShowPiece showPieceToChange = showPieceToFind.get();
            showPieceToChange.setOrderNum(orderNum);
            showPieceRepo.save(showPieceToChange);
        }
        return showPieceToFind;
    }


    @PostMapping("/remove-showpiece")
    public String deleteShowPieceFromDB(@RequestBody Show incomingShow) throws IOException {
        Optional<ShowPiece> showPieceToFind = showPieceRepo.findById(incomingShow.getId());

        if (showPieceToFind.isPresent()) {
            ShowPiece spToRemove = showPieceToFind.get();

            if (picRepo.existsByShowPiece(spToRemove)) {
                Collection<PlayerInChair> picsToRemove = picRepo.findAllByShowPiece(spToRemove);
                for (PlayerInChair pic : picsToRemove) {
                    picRepo.deleteById(pic.getId());
                }
            }

            showPieceRepo.delete(spToRemove);
            return "returned string";
        }
        return "nothing was deleted";
    }


    @PostMapping("/get-showtunes-on-program")
    public List<ShowPiece> getShowTunesOnAShow(@RequestBody Show incomingShow) throws IOException {
        try {
            List<ShowPiece> showTunes = (List<ShowPiece>) showPieceRepo.findAllByShow(incomingShow);
            Collections.sort(showTunes);
            return showTunes;
        } catch (
                Exception error) {
            error.printStackTrace();
        }
        return null;
    }

    @PostMapping("/get-pieces-on-program")
    public List<Piece> getPiecesOnAShow(@RequestBody Show incomingShow) throws IOException {

        List<Piece> piecesToReturn = new ArrayList<>();

        try {
            List<ShowPiece> showTunes = (List<ShowPiece>) showPieceRepo.findAllByShow(incomingShow);
            Collections.sort(showTunes);

            for (ShowPiece showPiece : showTunes) {
                piecesToReturn.add(showPiece.getPiece());
            }

        } catch (Exception error) {
            error.printStackTrace();
        }

        return piecesToReturn;
    }

}
