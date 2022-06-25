package com.rostermaker.demo.controllers;

import com.rostermaker.demo.legos.ShowPiece;
import com.rostermaker.demo.legos.emptyChair.Chair;
import com.rostermaker.demo.legos.playerInChair.PlayerInChair;
import com.rostermaker.demo.legos.playerInChair.PlayerInChairSorter;
import com.rostermaker.demo.models.piece.Piece;
import com.rostermaker.demo.models.show.Show;
import com.rostermaker.demo.repos.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@CrossOrigin
@RestController
public class ChairsRest {

    @Resource
    PlayerInChairRepo picRepo;

    @Resource
    PieceRepo pieceRepo;

    @Resource
    ShowRepo showRepo;

    @Resource
    ShowPieceRepo showPieceRepo;

    @Resource
    ChairRepo chairRepo;

    @Resource
    PlayerRepo playerRepo;

    @RequestMapping("/get-pics-in-show-piece")
    public Collection<PlayerInChair> getAllChairsInAPieceOnShow(@RequestBody ShowPiece incomingShowPiece) {
        Optional<ShowPiece> showPieceToFind = showPieceRepo.findById(incomingShowPiece.getId());
        if (showPieceToFind.isPresent()) {
            List<PlayerInChair> picsToReturn = (List<PlayerInChair>) picRepo.findAllByShowPiece(showPieceToFind.get());
            Collections.sort(picsToReturn);
            PlayerInChairSorter sorter = new PlayerInChairSorter(picsToReturn);
            return sorter.sort();
        }
        return null;
    }

    @RequestMapping("/get-pics-in-show")
    public Collection<PlayerInChair> getAllChairsInShow(@RequestBody Show incomingShow) {
        Optional<Show> showToFind = showRepo.findById(incomingShow.getId());

        if (showToFind.isPresent()) {
            List<PlayerInChair> picsToReturn = (List<PlayerInChair>) picRepo.findAllByShow(showToFind.get());
            Collections.sort(picsToReturn);

            PlayerInChairSorter sorter = new PlayerInChairSorter(picsToReturn);
            return sorter.sort();
        }

        return null;
    }


    @PostMapping("/add-empty-chairs/{pieceId}")
    public Optional<Piece> addFullOrchestration(@PathVariable Long pieceId, @RequestBody Collection<Chair> incomingChairs) throws IOException {
        Optional<Piece> pieceCheck = pieceRepo.findById(pieceId);

        try {
            if (pieceCheck.isPresent()) {
                Piece pieceForChairs = pieceCheck.get();

                for (Chair chair : incomingChairs) {
                    Chair chairToSave = new ChairBuilder()
                            .parts(chair.getParts())
                            .rank(chair.getRank())
                            .piece(pieceForChairs)
                            .specialDesignate(chair.getSpecialDesignate())
                            .build();
                    chairRepo.save(chairToSave);

                    if (showPieceRepo.existsByPiece(pieceForChairs)) {
                        for (ShowPiece showPiece : showPieceRepo.findAllByPiece(pieceForChairs)) {
                            picRepo.save(new PlayerInChair(showPiece, chairToSave));
                        }
                    }
                }
            }
        } catch (
                Exception error) {
            error.printStackTrace();
        }
        return pieceCheck;
    }

    @PostMapping("/add-chair-to-piece")
    public Optional<Piece> addChairToPiece(@RequestBody Chair incomingChair) throws IOException {
        Optional<Piece> pieceCheck = pieceRepo.findById(incomingChair.getPiece().getId());

        try {
            if (pieceCheck.isPresent()) {
                Piece pieceForChair = pieceCheck.get();
                Chair chairToSave = new ChairBuilder()
                        .parts(incomingChair.getParts())
                        .rank(incomingChair.getRank())
                        .piece(pieceForChair)
                        .specialDesignate(incomingChair.getSpecialDesignate())
                        .build();

                chairRepo.save(chairToSave);

                if (showPieceRepo.existsByPiece(pieceForChair)) {
                    for (ShowPiece showPiece : showPieceRepo.findAllByPiece(pieceForChair)) {
                        picRepo.save(new PlayerInChair(showPiece, chairToSave));
                    }
                }
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return pieceCheck;
    }

    @PostMapping("/add-chair-to-show")
    public Optional<Show> addChairToShow(@RequestBody Chair incomingChair) throws IOException {
        Optional<Show> showCheck = showRepo.findById(incomingChair.getShow().getId());

        try {
            if (showCheck.isPresent()) {
                Show showForChair = showCheck.get();

                Chair chairToSave = new ChairBuilder()
                        .parts(incomingChair.getParts())
                        .rank(incomingChair.getRank())
                        .show(showForChair)
                        .specialDesignate(incomingChair.getSpecialDesignate())
                        .build();
                chairRepo.save(chairToSave);
                picRepo.save(new PlayerInChair(showForChair, chairToSave));

            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return showCheck;
    }

    @PostMapping("/get-chairs-in-piece")
    public Collection<Chair> getOrchestrationOfPiece(@RequestBody Piece incomingPiece) throws IOException {
        Optional<Piece> pieceCheck = pieceRepo.findById(incomingPiece.getId());

        try {
            if (pieceCheck.isPresent()) {
                List<Chair> chairsToReturn = (List<Chair>) chairRepo.findAllByPiece(pieceCheck.get());
                Collections.sort(chairsToReturn);
                return chairsToReturn;
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return null;
    }


    @PostMapping("/get-possible-players")
    public List<Player> getPossiblePlayersForAChair(@RequestBody PlayerInChair incomingPIC) {

        try {
            List<Player> playersToSend = new ArrayList<>();
            Optional<PlayerInChair> picToFind = picRepo.findById(incomingPIC.getId());
            if (picToFind.isPresent()) {
                PlayerInChair foundPIC = picToFind.get();

                HashMap<Player, Boolean> eligiblePlayers = new HashMap<>();
                for (Player player : playerRepo.findAllByType(Type.CONTRACTED)) {
                    eligiblePlayers.put(player, true);
                }

                for (PlayerInChair picToCheck : picRepo.findAllByShowPiece(foundPIC.getShowPiece())) {
                    if (eligiblePlayers.containsKey(picToCheck.getPlayer())) {
                        eligiblePlayers.put(picToCheck.getPlayer(), false);
                    }
                }

                for (Player player : playerRepo.findAllByType(Type.CONTRACTED)) {
                    if (!player.couldSitHere(foundPIC)) {
                        eligiblePlayers.put(player, false);
                    }
                }

                for (Map.Entry<Player, Boolean> entry : eligiblePlayers.entrySet()) {
                    if (entry.getValue().equals(true)) {
                        playersToSend.add(entry.getKey());
                    }
                }

            }
            Collections.sort(playersToSend);
            return playersToSend;


        } catch (Exception error) {
            error.printStackTrace();
        }
        return null;
    }


    @PostMapping("/remove-player-from-pic")
    public Optional<PlayerInChair> removePlayerFromAChair(@RequestBody PlayerInChair incomingPIC) throws IOException {
        Optional<PlayerInChair> picToFind = picRepo.findById(incomingPIC.getId());

        try {
            picToFind.ifPresent(playerInChair -> {
                playerInChair.setPlayer(null);
                picRepo.save(playerInChair);
            });
        } catch (
                Exception error) {
            error.printStackTrace();
        }
        return picToFind;
    }

    @PostMapping("/put-player-in-pic/{picId}")
    public Optional<PlayerInChair> putAPlayerInAChair(@RequestBody Player incomingPlayer, @PathVariable Long picId) {

        try {
            Optional<PlayerInChair> premadePIC = picRepo.findById(picId);
            Optional<Player> playerToFind = playerRepo.findById(incomingPlayer.getId());
            if (premadePIC.isPresent() && playerToFind.isPresent()) {
                PlayerInChair pic = premadePIC.get();
                Player foundPlayer = playerToFind.get();
                boolean flagTest = false;

                ShowPiece possibleShowPiece;
                Show possibleShow;

                if (pic.getShow() == null) {
                    possibleShowPiece = pic.getShowPiece();
                    for (PlayerInChair pic1 : picRepo.findAllByShowPiece(possibleShowPiece)) {
                        if (pic1.hasThisPlayer(foundPlayer)) {
                            flagTest = true;
                        }
                    }
                } else {
                    possibleShow = pic.getShow();
                    for (PlayerInChair pic2 : picRepo.findAllByShow(possibleShow)) {
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

    @PostMapping("/delete-pic")
    public PlayerInChair deleteEntireChair(@RequestBody PlayerInChair picToRemove) throws IOException {

        try {
            Optional<PlayerInChair> pic = picRepo.findById(picToRemove.getId());
            pic.ifPresent(foundPic -> picRepo.deleteById(foundPic.getId()));
            return picToRemove;
        } catch (Exception error) {
            error.printStackTrace();
        }
        return null;
    }


    @PostMapping("/make-single-string-section-in-piece/{showPieceId}")
    public StringPartNum makeStringSection(@RequestBody StringPartNum sectionAndNumber, @PathVariable Long showPieceId) throws IOException {

        try {
            Optional<ShowPiece> showPieceToFind = showPieceRepo.findById(showPieceId);
            if (showPieceToFind.isPresent()) {

                ShowPiece retrievedShowPiece = showPieceToFind.get();
                if (chairRepo.existsByPrimaryPartAndPiece(sectionAndNumber.stringPart, retrievedShowPiece.getPiece())) {
                    Chair chairToReference = chairRepo.findByPrimaryPartAndPiece(sectionAndNumber.stringPart, retrievedShowPiece.getPiece());
                    for (int seat = 1; seat < sectionAndNumber.number; seat++) {
                        picRepo.save(new PlayerInChair(retrievedShowPiece, chairToReference, seat));
                    }
                }
            }
            return sectionAndNumber;
        } catch (Exception error) {
            error.printStackTrace();
        }
        return null;
    }


    @PostMapping("/make-single-string-section-in-show/{showId}")
    public StringPartNum makeStringSectionForPops(@RequestBody StringPartNum sectionAndNumber, @PathVariable Long showId) throws IOException {

        try {
            Optional<Show> showToFind = showRepo.findById(showId);
            if (showToFind.isPresent()) {

                Show retrievedShow = showToFind.get();
                if (chairRepo.existsByPrimaryPartAndShow(sectionAndNumber.stringPart, retrievedShow)) {
                    Chair chairToReference = chairRepo.findByPrimaryPartAndShow(sectionAndNumber.stringPart, retrievedShow);
                    for (int seat = 1; seat < sectionAndNumber.number; seat++) {
                        picRepo.save(new PlayerInChair(retrievedShow, chairToReference, seat));
                    }
                    return sectionAndNumber;

                }
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return null;
    }


    @PostMapping("/make-string-player-in-chairs/{showPieceId}")
    public void makeStringPICs(@RequestBody Collection<StringPartNum> incomingStringNumbers, @PathVariable Long showPieceId) throws IOException {

        try {
            Optional<ShowPiece> showPieceToFind = showPieceRepo.findById(showPieceId);
            if (showPieceToFind.isPresent()) {
                ShowPiece retrievedShowPiece = showPieceToFind.get();

                for (StringPartNum stringPartNum : incomingStringNumbers) {
                    if (chairRepo.existsByPrimaryPartAndPiece(stringPartNum.stringPart, retrievedShowPiece.getPiece())) {
                        Chair chairToReference = chairRepo.findByPrimaryPartAndPiece(stringPartNum.stringPart, retrievedShowPiece.getPiece());
                        for (int seat = 1; seat <= stringPartNum.number; seat++) {
                            picRepo.save(new PlayerInChair(retrievedShowPiece, chairToReference, seat));
                        }
                    }
                }
            }
        } catch (
                Exception error) {
            error.printStackTrace();

        }
    }


    @PostMapping("/change-seating")
    public Collection<PlayerInChair> changeSeatingOrder(@RequestBody Collection<PlayerInChair> pics) {

        try {
            for (PlayerInChair pic : pics) {
                Optional<PlayerInChair> picToFind = picRepo.findById(pic.getId());
                if (picToFind.isPresent()) {
                    PlayerInChair foundPic = picToFind.get();

                    if (!(foundPic.getPlayer() == null && pic.getPlayer() == null)) {
                        if ((foundPic.getPlayer() == null && pic.getPlayer() != null) || (foundPic.getPlayer() != null && pic.getPlayer() == null)) {
                            foundPic.setPlayer(pic.getPlayer());
                            picRepo.save(foundPic);
                        } else if (!foundPic.getPlayer().equals(pic.getPlayer())) {
                            foundPic.setPlayer(pic.getPlayer());
                            picRepo.save(foundPic);
                        }
                    }
                }
            }
            return pics;
        } catch (Exception error) {
            error.printStackTrace();
        }
        return null;

    }



}
