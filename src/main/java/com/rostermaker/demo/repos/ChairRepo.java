package com.rostermaker.demo.repos;

import com.rostermaker.demo.legos.emptyChair.Chair;
import com.rostermaker.demo.models.part.Part;
import com.rostermaker.demo.models.piece.Piece;
import com.rostermaker.demo.models.show.Show;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ChairRepo extends CrudRepository<Chair, Long> {
    Collection<Chair> findByPiece(Piece incomingPiece);

    boolean existsByPiece(Piece piece);

    Collection<Chair> findAllByPiece(Piece piece);


    boolean existsByPrimaryPartAndPiece(Part part, Piece piece);

    Chair findByPrimaryPartAndPiece(Part part, Piece piece);

//    boolean existsByPieceAndPrimaryPartAndRank(Piece piece, Part primaryPart, int rank);

//    Collection<Chair> findAllByPieceAndPrimaryPartAndRank(Piece piece, Part primaryPart, int rank);

//    boolean existsByShowAndPrimaryPartAndRank(Show show, Part part, int rank);

//    Collection<Chair> findAllByShowAndPrimaryPartAndRank(Show show, Part primaryPart, int rank);

    boolean existsByPrimaryPartAndShow(Part stringPart, Show retrievedShow);

    Chair findByPrimaryPartAndShow(Part stringPart, Show retrievedShow);


}
