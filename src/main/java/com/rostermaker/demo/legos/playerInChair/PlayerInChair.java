package com.rostermaker.demo.legos.playerInChair;


import com.rostermaker.demo.models.player.Player;
import com.rostermaker.demo.models.show.Show;
import com.rostermaker.demo.legos.ShowPiece;
import com.rostermaker.demo.legos.ShowPiece;
import com.rostermaker.demo.legos.emptyChair.Chair;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PlayerInChair implements Comparable<PlayerInChair> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private ShowPiece showPiece;

    @ManyToOne
    private Show show;

    @ManyToOne
    private Chair chair;

    public PlayerInChair() {
    }

    public PlayerInChair(ShowPiece showPiece, Chair chair) {
        this.showPiece = showPiece;
        this.chair = chair;
    }


    public PlayerInChair(Show showForChair, Chair chair) {
        this.show = showForChair;
        this.chair = chair;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setShowPiece(ShowPiece showPiece) {
        this.showPiece = showPiece;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public void setChair(Chair chair) {
        this.chair = chair;
    }

    public Long getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public ShowPiece getShowPiece() {
        return showPiece;
    }

    public Show getShow() {
        return show;
    }

    public Chair getChair() {
        return chair;
    }

    public boolean hasThisPlayer(Player incomingPlayer) {
        return player != null && player.equals(incomingPlayer);
    }


    @Override
    public int compareTo(PlayerInChair next) {
        return chair.compareTo(next.getChair());
    }
}


