package com.rostermaker.demo.repos;

import com.rostermaker.demo.legos.ShowPiece;
import com.rostermaker.demo.legos.playerInChair.PlayerInChair;
import com.rostermaker.demo.models.show.Show;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PlayerInChairRepo extends CrudRepository<PlayerInChair, Long> {
    Collection<PlayerInChair> findAllByShowPiece(ShowPiece showPiece);

    Collection<PlayerInChair> findAllByShow(Show show);

    boolean existsByShowPiece(ShowPiece showPiece);

    void deleteAllByShowPiece(ShowPiece showPiece);



}
