package com.rostermaker.demo.legos.playerInChair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HornChairSorter {

    Collection<PlayerInChair> picsToSort;

    public HornChairSorter(Collection<PlayerInChair> picsToSort) {
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
            Collections.swap(pics, principalIndex, assistantIndex);
        }
        return pics;
    }


}
