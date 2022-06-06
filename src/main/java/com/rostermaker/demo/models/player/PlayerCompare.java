package com.rostermaker.demo.models.player;

import com.rostermaker.demo.models.player.Player;

import java.util.Comparator;

public class PlayerCompare implements Comparator<Player> {

    public PlayerCompare() {
    }

    @Override
    public int compare(Player player1, Player player2) {
        if (player1.getPrimaryPart().compare(player2.getPrimaryPart()) != 0) {
            return player1.getPrimaryPart().compare(player2.getPrimaryPart());
        } else if (player1.getRank() < player2.getRank()) {
            return -1;
        } else {
            return 1;
        }
    }
}

//else {
//        return player1.getType().compare(player2.getType());
//        }
