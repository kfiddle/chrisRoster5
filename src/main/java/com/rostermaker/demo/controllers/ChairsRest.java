package com.rostermaker.demo.controllers;

import com.rostermaker.demo.enums.Type;
import com.rostermaker.demo.legos.ShowPiece;
import com.rostermaker.demo.legos.emptyChair.Chair;
import com.rostermaker.demo.legos.emptyChair.ChairBuilder;
import com.rostermaker.demo.legos.playerInChair.PlayerInChair;
import com.rostermaker.demo.legos.playerInChair.HornChairSorter;
import com.rostermaker.demo.models.instrument.Instrument;
import com.rostermaker.demo.models.part.Part;
import com.rostermaker.demo.models.piece.Piece;
import com.rostermaker.demo.models.player.Player;
import com.rostermaker.demo.models.show.Show;
import com.rostermaker.demo.repos.*;
import org.springframework.security.core.parameters.P;
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


//    @RequestMapping("/get-all-pics")
//    public Collection<PlayerInChair> getAllPics() {
//        return (Collection<PlayerInChair>) picRepo.findAll();
//    }

//    @RequestMapping("/get-pics-in-show-piece")
//    public Collection<PlayerInChair> getAllChairsInAPieceOnShow(@RequestBody ShowPiece incomingShowPiece) {
//        Optional<ShowPiece> showPieceToFind = showPieceRepo.findById(incomingShowPiece.getId());
//        if (showPieceToFind.isPresent()) {
//            List<PlayerInChair> picsToReturn = (List<PlayerInChair>) picRepo.findAllByShowPiece(showPieceToFind.get());
//            Collections.sort(picsToReturn);
//            HornChairSorter sorter = new HornChairSorter(picsToReturn);
//            List<PlayerInChair> sortedPics = sorter.sort();
//
//            for (PlayerInChair pic : sortedPics) {
//                Part part = pic.getChair().getPrimaryPart();
//                String instName = part.getInstrument().getName();
//                int instRank = part.getRank();
//                String assist = part.getSpecialDesignate();
//            }
//            return sorter.sort();
//        }
//        return null;
//    }

//    @RequestMapping("/get-pics-in-show")
//    public Collection<PlayerInChair> getAllChairsInShow(@RequestBody Show incomingShow) {
//        Optional<Show> showToFind = showRepo.findById(incomingShow.getId());
//        if (showToFind.isPresent()) {
//            List<PlayerInChair> picsToReturn = (List<PlayerInChair>) picRepo.findAllByShow(showToFind.get());
//            Collections.sort(picsToReturn);
//            HornChairSorter sorter = new HornChairSorter(picsToReturn);
//            return sorter.sort();
//        }
//        return null;
//    }

//    public Chair addChair(Chair incomingChair) {
//        Piece piece = null;
//        Show show = null;
//        Chair chairToSave = null;
//
//        if (incomingChair.getPiece() != null) {
//            Optional<Piece> pieceToFind = pieceRepo.findById(incomingChair.getPiece().getId());
//            if (pieceToFind.isPresent()) {
//                piece = pieceToFind.get();
//            }
//        } else if (incomingChair.getShow() != null) {
//            Optional<Show> showToFind = showRepo.findById(incomingChair.getShow().getId());
//            if (showToFind.isPresent()) {
//                show = showToFind.get();
//            }
//        }
//
//        List<Part> partsInNewChair = new ArrayList<>();
//
//        for (Part part : incomingChair.getParts()) {
//            Instrument inst;
//            if (part.getInstrument().getName() != null) {
//                inst = instrumentRepo.findByName(part.getInstrument().getName());
//            } else {
//                inst = instrumentRepo.findByAbbreviation(part.getInstrument().getAbbreviation());
//            }
//
//            Part partToAdd = new Part(inst);
//            if (part.getRank() > 0) {
//                partToAdd.setRank(part.getRank());
//            } else if (part.getSpecialDesignate() != null) {
//                partToAdd.setSpecialDesignate(part.getSpecialDesignate());
//            }
//
//            partsInNewChair.add(partToAdd);
//        }
//
//        chairToSave = new ChairBuilder()
//                .parts(partsInNewChair)
//                .piece(piece)
//                .show(show)
//                .build();
//        chairRepo.save(chairToSave);
//
//        if (chairToSave.getShow() != null) {
//            picRepo.save(new PlayerInChair(show, chairToSave));
//        }
//
//        if (piece != null && showPieceRepo.existsByPiece(piece)) {
//            for (ShowPiece showPiece : showPieceRepo.findAllByPiece(piece)) {
//                picRepo.save(new PlayerInChair(showPiece, chairToSave));
//            }
//        }
//        return chairToSave;
//    }

//    @PostMapping("/add-chair")
//    public Chair addChairWrapper(@RequestBody Chair incomingChair) throws IOException {
//        try {
//            return addChair(incomingChair);
//        } catch (Exception error) {
//            error.printStackTrace();
//        }
//        return null;
//    }
//
//    @PostMapping("/add-empty-chairs")
//    public Collection<Chair> addChairsCollection(@RequestBody Collection<Chair> incomingChairs) throws IOException {
//        Collection<Chair> chairsToReturn = new ArrayList<>();
//
//        try {
//            for (Chair chair : incomingChairs) {
//                chairsToReturn.add(addChair(chair));
//            }
//        } catch (Exception error) {
//            error.printStackTrace();
//        }
//        return chairsToReturn;
//    }


//    @PostMapping("/add-empty-chairs/{pieceId}")
//    public Optional<Piece> addFullOrchestration(@PathVariable Long pieceId, @RequestBody Collection<Chair> incomingChairs) throws IOException {
//        Optional<Piece> pieceCheck = pieceRepo.findById(pieceId);
//
//        try {
//            if (pieceCheck.isPresent()) {
//                Piece pieceForChairs = pieceCheck.get();
//
//                for (Chair chair : incomingChairs) {
//                    List<Part> partsInNewChair = new ArrayList<>();
//
//                    for (Part part : chair.getParts()) {
//                        String abbreviation = part.getInstrument().getAbbreviation();
//
//                        if (instrumentRepo.existsByAbbreviation(abbreviation)) {
//                            Instrument inst = instrumentRepo.findByAbbreviation(abbreviation);
//
//                            Part partToAdd = new Part(inst);
//                            if (part.getRank() > 0) {
//                                partToAdd.setRank(part.getRank());
//                            } else if (part.getSpecialDesignate() != null) {
//                                partToAdd.setSpecialDesignate(part.getSpecialDesignate());
//                            }
//
//                            partsInNewChair.add(partToAdd);
//                        }
//                    }
//
//                    Chair chairToSave = new ChairBuilder()
//                            .parts(partsInNewChair)
//                            .piece(pieceForChairs)
//                            .build();
//                    chairRepo.save(chairToSave);
//
//                    if (showPieceRepo.existsByPiece(pieceForChairs)) {
//                        for (ShowPiece showPiece : showPieceRepo.findAllByPiece(pieceForChairs)) {
//                            picRepo.save(new PlayerInChair(showPiece, chairToSave));
//                        }
//                    }
//                }
//            }
//        } catch (
//                Exception error) {
//            error.printStackTrace();
//        }
//        return pieceCheck;
//    }


//    @PostMapping("/add-chairs-to-piece/{pieceId}")
//    public Collection<Chair> addChairs(@RequestBody Collection<Chair> incomingChairs, @PathVariable Long pieceId) throws IOException {
//        try {
//
//            System.out.println(incomingChairs.size());
//
//
//        } catch (Exception error) {
//            error.printStackTrace();
//        }
//
//        return incomingChairs;
//    }

//    @PostMapping("/add-chair-to-piece")
//    public Optional<Piece> addChairToPiece(@RequestBody Chair incomingChair) throws IOException {
//        Optional<Piece> pieceCheck = pieceRepo.findById(incomingChair.getPiece().getId());
//
//        try {
//            if (pieceCheck.isPresent()) {
//                Piece pieceForChair = pieceCheck.get();
//                Chair chairToSave = new ChairBuilder()
//                        .parts(incomingChair.getParts())
//                        .piece(pieceForChair)
//                        .build();
//
//                chairRepo.save(chairToSave);
//
//                if (showPieceRepo.existsByPiece(pieceForChair)) {
//                    for (ShowPiece showPiece : showPieceRepo.findAllByPiece(pieceForChair)) {
//                        picRepo.save(new PlayerInChair(showPiece, chairToSave));
//                    }
//                }
//            }
//        } catch (Exception error) {
//            error.printStackTrace();
//        }
//        return pieceCheck;
//    }

//    @PostMapping("/add-chair-to-show")
//    public Optional<Show> addChairToShow(@RequestBody Chair incomingChair) throws IOException {
//        Optional<Show> showCheck = showRepo.findById(incomingChair.getShow().getId());
//
//        try {
//            if (showCheck.isPresent()) {
//                Show showForChair = showCheck.get();
//
//                Chair chairToSave = new ChairBuilder()
//                        .parts(incomingChair.getParts())
//                        .show(showForChair)
//                        .build();
//                chairRepo.save(chairToSave);
//                picRepo.save(new PlayerInChair(showForChair, chairToSave));
//
//            }
//        } catch (Exception error) {
//            error.printStackTrace();
//        }
//        return showCheck;
//    }

//    @PostMapping("/get-chairs-in-piece")
//    public Collection<Chair> getOrchestrationOfPiece(@RequestBody Piece incomingPiece) throws IOException {
//        Optional<Piece> pieceCheck = pieceRepo.findById(incomingPiece.getId());
//
//        try {
//            if (pieceCheck.isPresent()) {
//                List<Chair> chairsToReturn = (List<Chair>) chairRepo.findAllByPiece(pieceCheck.get());
//                Collections.sort(chairsToReturn);
//                return chairsToReturn;
//            }
//        } catch (Exception error) {
//            error.printStackTrace();
//        }
//        return null;
//    }

//    @PostMapping("/get-possible-players")
//    public List<Player> getPossiblePlayersForAChair(@RequestBody PlayerInChair incomingPIC) {
//
//        try {
//            List<Player> playersToSend = new ArrayList<>();
//            Optional<PlayerInChair> picToFind = picRepo.findById(incomingPIC.getId());
//            if (picToFind.isPresent()) {
//                PlayerInChair foundPIC = picToFind.get();
//
//                HashMap<Player, Boolean> eligiblePlayers = new HashMap<>();
//                for (Player player : playerRepo.findAllByType(Type.CONTRACTED)) {
//                    eligiblePlayers.put(player, true);
//                }
//
//                for (PlayerInChair picToCheck : picRepo.findAllByShowPiece(foundPIC.getShowPiece())) {
//                    if (eligiblePlayers.containsKey(picToCheck.getPlayer())) {
//                        eligiblePlayers.put(picToCheck.getPlayer(), false);
//                    }
//                }
//
//                for (Player player : playerRepo.findAllByType(Type.CONTRACTED)) {
//                    if (!player.couldSitHere(foundPIC)) {
//                        eligiblePlayers.put(player, false);
//                    }
//                }
//
//                for (Map.Entry<Player, Boolean> entry : eligiblePlayers.entrySet()) {
//                    if (entry.getValue().equals(true)) {
//                        playersToSend.add(entry.getKey());
//                    }
//                }
//
//            }
//            Collections.sort(playersToSend);
//            return playersToSend;
//
//
//        } catch (Exception error) {
//            error.printStackTrace();
//        }
//        return null;
//    }


//    @PostMapping("/remove-player-from-pic")
//    public Optional<PlayerInChair> removePlayerFromAChair(@RequestBody PlayerInChair incomingPIC) throws
//            IOException {
//        Optional<PlayerInChair> picToFind = picRepo.findById(incomingPIC.getId());
//
//        try {
//            picToFind.ifPresent(playerInChair -> {
//                playerInChair.setPlayer(null);
//                picRepo.save(playerInChair);
//            });
//        } catch (
//                Exception error) {
//            error.printStackTrace();
//        }
//        return picToFind;
//    }

//    @PostMapping("/put-player-in-pic/{picId}")
//    public Optional<PlayerInChair> putAPlayerInAChair(@RequestBody Player incomingPlayer, @PathVariable Long
//            picId) {
//
//        try {
//            Optional<PlayerInChair> premadePIC = picRepo.findById(picId);
//            Optional<Player> playerToFind = playerRepo.findById(incomingPlayer.getId());
//            if (premadePIC.isPresent() && playerToFind.isPresent()) {
//                PlayerInChair pic = premadePIC.get();
//                Player foundPlayer = playerToFind.get();
//                boolean flagTest = false;
//
//                ShowPiece possibleShowPiece;
//                Show possibleShow;
//
//                if (pic.getShow() == null) {
//                    possibleShowPiece = pic.getShowPiece();
//                    for (PlayerInChair pic1 : picRepo.findAllByShowPiece(possibleShowPiece)) {
//                        if (pic1.hasThisPlayer(foundPlayer)) {
//                            flagTest = true;
//                        }
//                    }
//                } else {
//                    possibleShow = pic.getShow();
//                    for (PlayerInChair pic2 : picRepo.findAllByShow(possibleShow)) {
//                        if (pic2.hasThisPlayer(foundPlayer)) {
//                            flagTest = true;
//                        }
//                    }
//                }
//
//                if (!flagTest) {
//                    pic.setPlayer(foundPlayer);
//                    picRepo.save(pic);
//                    return premadePIC;
//                }
//
//            }
//        } catch (
//                Exception error) {
//            error.printStackTrace();
//
//        }
//        return Optional.empty();
//    }

//    @PostMapping("/delete-pic")
//    public PlayerInChair deleteEntireChair(@RequestBody PlayerInChair picToRemove) throws IOException {
//
//        try {
//            Optional<PlayerInChair> pic = picRepo.findById(picToRemove.getId());
//            pic.ifPresent(foundPic -> picRepo.deleteById(foundPic.getId()));
//            return picToRemove;
//        } catch (Exception error) {
//            error.printStackTrace();
//        }
//        return null;
//    }


//    @PostMapping("/make-single-string-section-in-piece/{showPieceId}")
//    public Part makeStringSection(@RequestBody Part sectionAndNumber, @PathVariable Long showPieceId) throws
//            IOException {
//
//        try {
//            Optional<ShowPiece> showPieceToFind = showPieceRepo.findById(showPieceId);
//            if (showPieceToFind.isPresent()) {
//
//                ShowPiece retrievedShowPiece = showPieceToFind.get();
//                Part partToReference = new Part(sectionAndNumber.getInstrument(), 1);
//                if (chairRepo.existsByPrimaryPartAndPiece(partToReference, retrievedShowPiece.getPiece())) {
//                    for (int seat = 2; seat <= sectionAndNumber.getRank(); seat++) {
//                        List<Part> parts = Collections.singletonList(new Part(sectionAndNumber.getInstrument(), seat));
//                        Chair chair = new ChairBuilder().parts(parts).piece(retrievedShowPiece.getPiece()).build();
//                        picRepo.save(new PlayerInChair(retrievedShowPiece, chair));
//                    }
//                }
//            }
//            return sectionAndNumber;
//        } catch (Exception error) {
//            error.printStackTrace();
//        }
//        return null;
//    }


//    @PostMapping("/make-single-string-section-in-show/{showId}")
//    public Part makeStringSectionForPops(@RequestBody Part sectionAndNumber, @PathVariable Long showId) throws
//            IOException {
//
//        try {
//            Optional<Show> showToFind = showRepo.findById(showId);
//            if (showToFind.isPresent()) {
//
//                Show retrievedShow = showToFind.get();
//                Part partToReference = new Part(sectionAndNumber.getInstrument(), 1);
//                if (chairRepo.existsByPrimaryPartAndShow(partToReference, retrievedShow)) {
//                    for (int seat = 1; seat < sectionAndNumber.getRank(); seat++) {
//                        List<Part> parts = Collections.singletonList(new Part(sectionAndNumber.getInstrument(), seat));
//
//                        Chair chair = new ChairBuilder().parts(parts).show(retrievedShow).build();
//                        picRepo.save(new PlayerInChair(retrievedShow, chair));
//                    }
//                    return sectionAndNumber;
//                }
//            }
//        } catch (Exception error) {
//            error.printStackTrace();
//        }
//        return null;
//    }


//    @PostMapping("/change-seating")
//    public Collection<PlayerInChair> changeSeatingOrder(@RequestBody Collection<PlayerInChair> pics) {
//
//        try {
//            for (PlayerInChair pic : pics) {
//                Optional<PlayerInChair> picToFind = picRepo.findById(pic.getId());
//                if (picToFind.isPresent()) {
//                    PlayerInChair foundPic = picToFind.get();
//
//                    if (!(foundPic.getPlayer() == null && pic.getPlayer() == null)) {
//                        if ((foundPic.getPlayer() == null && pic.getPlayer() != null) || (foundPic.getPlayer() != null && pic.getPlayer() == null)) {
//                            foundPic.setPlayer(pic.getPlayer());
//                            picRepo.save(foundPic);
//                        } else if (!foundPic.getPlayer().equals(pic.getPlayer())) {
//                            foundPic.setPlayer(pic.getPlayer());
//                            picRepo.save(foundPic);
//                        }
//                    }
//                }
//            }
//            return pics;
//        } catch (Exception error) {
//            error.printStackTrace();
//        }
//        return null;
//    }
}
