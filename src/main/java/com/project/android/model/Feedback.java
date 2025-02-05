package com.project.android.model;

public class Feedback {
    private long feedbackID;
    private String description;
    private long touristID;
    private long cloakRoomID;
    private long locationID;
    private long touristSpotID;

    public long getTouristID() {
        return touristID;
    }

    public void setTouristID(long touristID) {
        this.touristID = touristID;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getFeedbackID() {

        return feedbackID;
    }

    public void setFeedbackID(long feedbackID) {
        this.feedbackID = feedbackID;
    }

    public long getCloakRoomID() {
        return cloakRoomID;
    }

    public void setCloakRoomID(long cloakRoomID) {
        this.cloakRoomID = cloakRoomID;
    }

    public long getLocationID() {
        return locationID;
    }

    public void setLocationID(long locationID) {
        this.locationID = locationID;
    }

    public long getTouristSpotID() {
        return touristSpotID;
    }

    public void setTouristSpotID(long touristSpotID) {
        this.touristSpotID = touristSpotID;
    }
}
