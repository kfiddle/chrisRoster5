package com.rostermaker.demo.models.player;


import com.rostermaker.demo.enums.Part;
import com.rostermaker.demo.enums.Type;
import com.rostermaker.demo.legos.emptyChair.Chair;
import com.rostermaker.demo.legos.playerInChair.PlayerInChair;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Player implements Comparable<Player> {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private int rank;

    @ElementCollection
    private List<Part> parts = new ArrayList<>();

    private Part primaryPart;

    private String firstNameArea;
    private String lastName;
    private String email;
    private String homePhone;
    private String cellPhone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zip;
    private String password;

    public Player() {
    }

    public Player(PlayerBuilder playerBuilder) {
        firstNameArea = playerBuilder.firstNameArea;
        lastName = playerBuilder.lastName;
        type = playerBuilder.type;
        rank = playerBuilder.rank;
        email = playerBuilder.email;
        homePhone = playerBuilder.homePhone;
        cellPhone = playerBuilder.cellPhone;
        addressLine1 = playerBuilder.addressLine1;
        addressLine2 = playerBuilder.addressLine2;
        city = playerBuilder.city;
        state = playerBuilder.state;
        zip = playerBuilder.zip;
        parts = playerBuilder.parts;
        if (playerBuilder.parts.size() > 0) {
            primaryPart = playerBuilder.parts.get(0);
        }
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public void setPrimaryPart(Part primaryPart) {
        this.primaryPart = primaryPart;
    }

    public void setFirstNameArea(String firstNameArea) {
        this.firstNameArea = firstNameArea;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public int getRank() {
        return rank;
    }

    public List<Part> getParts() {
        return parts;
    }

    public Part getPrimaryPart() {
        return primaryPart;
    }

    public String getFirstNameArea() {
        return firstNameArea;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPassword() {
        return password;
    }

    //    public boolean couldSitHere(PlayerInChair pic) {
//        Chair chair = pic.getChair();
//
//        if (rank > chair.getRank()) {
//            return false;
//        } else {
//            for (Part chairPart : chair.getParts()) {
//                if (!parts.contains(chairPart)) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

    public boolean couldSitHere(PlayerInChair pic) {
        Chair chair = pic.getChair();
        for (Part chairPart : chair.getParts()) {
            if (!parts.contains(chairPart)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public int compareTo(Player otherPlayer) {
        if (primaryPart.compare(otherPlayer.getPrimaryPart()) != 0) {
            return primaryPart.compare(otherPlayer.getPrimaryPart());
        } else if (rank < otherPlayer.getRank()) {
            return -1;
        } else return type.compare(otherPlayer.getType());
    }
}
