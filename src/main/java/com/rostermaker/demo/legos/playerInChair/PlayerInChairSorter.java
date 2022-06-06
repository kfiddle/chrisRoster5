package com.rostermaker.demo.legos.playerInChair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayerInChairSorter {

    Collection<com.example.demo.legos.playerInChair.PlayerInChair> picsToSort;

    public PlayerInChairSorter(Collection<com.example.demo.legos.playerInChair.PlayerInChair> picsToSort) {
        this.picsToSort = picsToSort;
    }

    public List<com.example.demo.legos.playerInChair.PlayerInChair> sort() {
        ArrayList<com.example.demo.legos.playerInChair.PlayerInChair> pics = new ArrayList<com.example.demo.legos.playerInChair.PlayerInChair>(picsToSort);

        int assistantIndex = 0;
        int principalIndex = 0;
        boolean assistantExists = false;
        for (com.example.demo.legos.playerInChair.PlayerInChair pic : pics) {
            if (pic.getChair().isPrincipalHorn()) {
                principalIndex = pics.indexOf(pic);
            }
            if (pic.getChair().hasAssDesignate()) {
                assistantIndex = pics.indexOf(pic);
                assistantExists = true;
            }
        }

        if (assistantExists) {
            pics.add(principalIndex + 1, pics.get(assistantIndex));
            pics.remove(assistantIndex + 1);
        }
        return pics;
    }


}
