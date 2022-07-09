package com.rostermaker.demo.controllers;

import com.rostermaker.demo.enums.Type;
import com.rostermaker.demo.legos.ShowPiece;
import com.rostermaker.demo.legos.emptyChair.Chair;
import com.rostermaker.demo.legos.emptyChair.ChairBuilder;
import com.rostermaker.demo.legos.playerInChair.PlayerInChair;
import com.rostermaker.demo.legos.playerInChair.PlayerInChairSorter;
import com.rostermaker.demo.models.part.Part;
import com.rostermaker.demo.models.piece.Piece;
import com.rostermaker.demo.models.piece.StringPartNum;
import com.rostermaker.demo.models.player.Player;
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
    InstrumentRepo instrumentRepo;

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
                    List<Part> partsInNewChair = new ArrayList<>();
//
//                    for (Part part : chair.getParts()) {
//                        if (instrumentRepo.existsByName())
//                    }



                    Chair chairToSave = new ChairBuilder()
//                            .primaryPart(chair.getPrimaryPart())
//                            .otherParts(chair.getOtherParts())
                            .parts(chair.getParts())
                            .piece(pieceForChairs)
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


    @PostMapping("/add-chairs-to-piece/{pieceId}")
    public Collection<Chair> addChairs(@RequestBody Collection<Chair> incomingChairs, @PathVariable Long pieceId) throws IOException {
        try {

            System.out.println(incomingChairs.size());


        } catch (Exception error) {
            error.printStackTrace();
        }

        return incomingChairs;
    }

    @PostMapping("/add-chair-to-piece")
    public Optional<Piece> addChairToPiece(@RequestBody Chair incomingChair) throws IOException {
        Optional<Piece> pieceCheck = pieceRepo.findById(incomingChair.getPiece().getId());

        try {
            if (pieceCheck.isPresent()) {
                Piece pieceForChair = pieceCheck.get();
                Chair chairToSave = new ChairBuilder()
                        .parts(incomingChair.getParts())
//                        .primaryPart(incomingChair.getPrimaryPart())
//                        .otherParts(incomingChair.getOtherParts())
                        .piece(pieceForChair)
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

//                        .primaryPart(incomingChair.getPrimaryPart())
//                        .otherParts(incomingChair.getOtherParts())
                        .show(showForChair)
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
    public Part makeStringSection(@RequestBody Part sectionAndNumber, @PathVariable Long showPieceId) throws IOException {

        try {
            Optional<ShowPiece> showPieceToFind = showPieceRepo.findById(showPieceId);
            if (showPieceToFind.isPresent()) {

                ShowPiece retrievedShowPiece = showPieceToFind.get();
                Part partToReference = new Part(sectionAndNumber.getInstrument(), 1);
                if (chairRepo.existsByPrimaryPartAndPiece(partToReference, retrievedShowPiece.getPiece())) {
                    for (int seat = 2; seat <= sectionAndNumber.getRank(); seat++) {
//                        Part part = new Part(sectionAndNumber.getInstrument(), seat);
                        List<Part> parts = Collections.singletonList(new Part(sectionAndNumber.getInstrument(), seat));

//                        Chair chair = new ChairBuilder().primaryPart(part).piece(retrievedShowPiece.getPiece()).build();
                        Chair chair = new ChairBuilder().parts(parts).piece(retrievedShowPiece.getPiece()).build();
                        picRepo.save(new PlayerInChair(retrievedShowPiece, chair));
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
    public Part makeStringSectionForPops(@RequestBody Part sectionAndNumber, @PathVariable Long showId) throws IOException {

        try {
            Optional<Show> showToFind = showRepo.findById(showId);
            if (showToFind.isPresent()) {

                Show retrievedShow = showToFind.get();
                Part partToReference = new Part(sectionAndNumber.getInstrument(), 1);
                if (chairRepo.existsByPrimaryPartAndShow(partToReference, retrievedShow)) {
                    for (int seat = 1; seat < sectionAndNumber.getRank(); seat++) {
//                        Part part = new Part(sectionAndNumber.getInstrument(), seat);
                        List<Part> parts = Collections.singletonList(new Part(sectionAndNumber.getInstrument(), seat));

//                        Chair chair = new ChairBuilder().primaryPart(part).show(retrievedShow).build();
                        Chair chair = new ChairBuilder().parts(parts).show(retrievedShow).build();
                        picRepo.save(new PlayerInChair(retrievedShow, chair));
                    }
                    return sectionAndNumber;
                }
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return null;
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
