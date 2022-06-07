package com.rostermaker.demo.models.player;

import com.rostermaker.demo.enums.Type;
import com.rostermaker.demo.models.player.Player;

import java.util.Optional;

public class PlayerEditor {

    Player storedPlayer;

    public PlayerEditor(Player storedPlayer) {
        this.storedPlayer = storedPlayer;
    }

    public void editFrom(Player incoming) {
        Optional<Type> typeOpt = Optional.ofNullable(incoming.getType());

        int rank = incoming.getRank();

        if (rank > 0) {
            storedPlayer.setRank(rank);
        }

        typeOpt.ifPresent(gotten -> storedPlayer.setType(gotten));

        Optional<String> firstNameOpt = Optional.ofNullable(incoming.getFirstNameArea());
        Optional<String> lastNameOpt = Optional.ofNullable(incoming.getLastName());
        Optional<String> emailOpt = Optional.ofNullable(incoming.getEmail());
        Optional<String> homePhoneOpt = Optional.ofNullable(incoming.getHomePhone());
        Optional<String> cellPhoneOpt = Optional.ofNullable(incoming.getCellPhone());
        Optional<String> addressLine1Opt = Optional.ofNullable(incoming.getAddressLine1());
        Optional<String> addressLine2Opt = Optional.ofNullable(incoming.getAddressLine2());
        Optional<String> cityOpt = Optional.ofNullable(incoming.getCity());
        Optional<String> stateOpt = Optional.ofNullable(incoming.getState());
        Optional<String> zipOpt = Optional.ofNullable(incoming.getZip());

        firstNameOpt.ifPresent(gotten -> storedPlayer.setFirstNameArea(gotten));
        lastNameOpt.ifPresent(gotten -> storedPlayer.setLastName(gotten));
        emailOpt.ifPresent(gotten -> storedPlayer.setEmail(gotten));
        homePhoneOpt.ifPresent(gotten -> storedPlayer.setHomePhone(gotten));
        cellPhoneOpt.ifPresent(gotten -> storedPlayer.setCellPhone(gotten));
        addressLine1Opt.ifPresent(gotten -> storedPlayer.setAddressLine1(gotten));
        addressLine2Opt.ifPresent(gotten -> storedPlayer.setAddressLine2(gotten));
        cityOpt.ifPresent(gotten -> storedPlayer.setCity(gotten));
        stateOpt.ifPresent(gotten -> storedPlayer.setState(gotten));
        zipOpt.ifPresent(gotten -> storedPlayer.setZip(gotten));




    }
}
