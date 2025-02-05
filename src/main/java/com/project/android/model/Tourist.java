package com.project.android.model;

public class Tourist {
    private long touristID;
    private String mail;
    private String name;
    private String username;
    private String password;
    private String profilePath;
    private String contact;

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

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {

        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public long getTouristID() {

        return touristID;
    }

    public void setTouristID(long touristID) {
        this.touristID = touristID;
    }
}
