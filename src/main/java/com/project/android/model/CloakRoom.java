package com.project.android.model;

public class CloakRoom {
    private long cloakRoomID;
    private String name;
    private String profilePath;
    private String contact;
    private String address;
    private String opensAt;
    private String closesAt;
    private String perHourCharges;
    private long ownerID;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProfilePath() {

        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getCloakRoomID() {

        return cloakRoomID;
    }

    public void setCloakRoomID(long cloakRoomID) {
        this.cloakRoomID = cloakRoomID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpensAt() {
        return opensAt;
    }

    public void setOpensAt(String opensAt) {
        this.opensAt = opensAt;
    }

    public String getClosesAt() {
        return closesAt;
    }

    public void setClosesAt(String closesAt) {
        this.closesAt = closesAt;
    }

    public String getPerHourCharges() {
        return perHourCharges;
    }

    public void setPerHourCharges(String perHourCharges) {
        this.perHourCharges = perHourCharges;
    }

    public long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(long ownerID) {
        this.ownerID = ownerID;
    }
}
