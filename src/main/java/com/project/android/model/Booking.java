package com.project.android.model;

import java.util.Calendar;

public class Booking
{
    private long bookingID;
    private long touristID;
    private long cloakRoomID;
    private Calendar date;
    private int otp;

    public long getBookingID() {
        return bookingID;
    }

    public void setBookingID(long bookingID) {
        this.bookingID = bookingID;
    }

    public long getTouristID() {
        return touristID;
    }

    public void setTouristID(long touristID) {
        this.touristID = touristID;
    }

    public long getCloakRoomID() {
        return cloakRoomID;
    }

    public void setCloakRoomID(long cloakRoomID) {
        this.cloakRoomID = cloakRoomID;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}
