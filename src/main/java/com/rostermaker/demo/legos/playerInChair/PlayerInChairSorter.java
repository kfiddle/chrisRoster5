package com.rostermaker.demo.legos.playerInChair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayerInChairSorter {

    Collection<PlayerInChair> picsToSort;

    public PlayerInChairSorter(Collection<PlayerInChair> picsToSort) {
        this.picsToSort = picsToSort;
    }

    public List<PlayerInChair> sort() {
        ArrayList<PlayerInChair> pics = new ArrayList<PlayerInChair>(picsToSort);

        int assistantIndex = 0;
        int principalIndex = 0;
        boolean assistantExists = false;
        for (PlayerInChair pic : pics) {
            if (pic.getChair().getPrimaryPart().isPrincipalHorn()) {
                principalIndex = pics.indexOf(pic);
            }
            if (pic.getChair().getPrimaryPart().hasAssDesignate()) {
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
