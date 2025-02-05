package com.project.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.project.android.model.Booking;
import com.project.android.model.CloakRoom;
import com.project.android.model.CloakRoomOwner;
import com.project.android.model.Feedback;
import com.project.android.model.Tourist;
import com.project.android.utility.Constants;
import com.project.android.utility.Utility;

public class AppDatabaseHelper extends SQLiteOpenHelper {
    public AppDatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CLOAKROOMOWNER_TABLE = "CREATE TABLE " + Constants.CLOAKROOMOWNER_TABLE_NAME + "(" +
                Constants.ID_KEY + " INTEGER PRIMARY KEY," +
                Constants.CLOAKROOMOWNER_NAME_KEY + " TEXT," +
                Constants.CLOAKROOMOWNER_MAIL_KEY + " TEXT," +
                Constants.CLOAKROOMOWNER_CONTACT_KEY + " TEXT," +
                Constants.CLOAKROOMOWNER_USERNAME_KEY + " TEXT," +
                Constants.CLOAKROOMOWNER_PASSWORD_KEY + " TEXT," +
                Constants.CLOAKROOM_PROFILE_PATH_KEY + " TEXT " +
                ")";

        String CREATE_CLOAKROOM_TABLE = "CREATE TABLE " + Constants.CLOAKROOM_TABLE_NAME + "(" +
                Constants.ID_KEY + " INTEGER PRIMARY KEY," +
                Constants.CLOAKROOM_NAME_KEY + " TEXT," +
                Constants.CLOAKROOM_ADDRESS_KEY + " TEXT," +
                Constants.CLOAKROOM_CONTACT_KEY + " TEXT," +
                Constants.CLOAKROOM_OPENING_TIME_KEY + " TEXT," +
                Constants.CLOAKROOM_CLOSING_TIME_KEY + " TEXT," +
                Constants.CLOAKROOM_PROFILE_PATH_KEY + " TEXT," +
                Constants.CLOAKROOM_OWNER_ID_KEY + " INTEGER," +
                Constants.CLOAKROOM_PER_HOUR_CHARGES_KEY + " TEXT " +
                ")";

        String CREATE_FEEDBACK_TABLE = "CREATE TABLE " + Constants.FEEDBACK_TABLE_NAME + "(" +
                Constants.ID_KEY + " INTEGER PRIMARY KEY," +
                Constants.FEEDBACK_DESCRIPTION_KEY + " TEXT," +
                Constants.CLOAKROOM_ID_KEY + " INTEGER," +
                Constants.LOCATION_ID_KEY + " INTEGER," +
                Constants.TOURIST_SPOT_ID_KEY + " INTEGER," +
                Constants.TOURIST_ID_KEY + " INTEGER " +
                ")";

        String CREATE_TOURIST_TABLE = "CREATE TABLE " + Constants.TOURIST_TABLE_NAME + "(" +
                Constants.ID_KEY + " INTEGER PRIMARY KEY," +
                Constants.TOURIST_NAME_KEY + " TEXT," +
                Constants.TOURIST_PASSWORD_KEY + " TEXT," +
                Constants.TOURIST_MAIL_KEY + " TEXT," +
                Constants.TOURIST_CONTACT_KEY + " TEXT," +
                Constants.TOURIST_PROFILEPATH_KEY + " TEXT," +
                Constants.TOURIST_USERNAME_KEY + " TEXT " +
                ")";

        String CREATE_BOOKING_TABLE = "CREATE TABLE " + Constants.BOOKING_TABLE_NAME + "("
                + Constants.ID_KEY + " INTEGER PRIMARY KEY," +
                Constants.BOOKING_DATE_KEY + " TEXT," +
                Constants.TOURIST_ID_KEY + " INTEGER," +
                Constants.OTP_KEY + " INTEGER," +
                Constants.CLOAKROOM_ID_KEY + " INTEGER " +
                 ")";

        db.execSQL(CREATE_CLOAKROOMOWNER_TABLE);
        db.execSQL(CREATE_CLOAKROOM_TABLE);
        db.execSQL(CREATE_FEEDBACK_TABLE);
        db.execSQL(CREATE_TOURIST_TABLE);
        db.execSQL(CREATE_BOOKING_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long addTourist(Tourist tourist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // Inserting Row to User table
        values.put(Constants.TOURIST_NAME_KEY, tourist.getName());
        values.put(Constants.TOURIST_USERNAME_KEY, tourist.getUsername());
        values.put(Constants.TOURIST_PASSWORD_KEY, tourist.getPassword());
        values.put(Constants.TOURIST_MAIL_KEY, tourist.getMail());
        values.put(Constants.TOURIST_PROFILEPATH_KEY, tourist.getProfilePath());
        values.put(Constants.TOURIST_CONTACT_KEY, tourist.getContact());

        long visitorID = db.insert(Constants.TOURIST_TABLE_NAME, null, values);

        db.close();
        return visitorID;
    }

    public long addCloakRoomOwner(CloakRoomOwner cloakRoomOwner) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // Inserting Row to User table
        values.put(Constants.CLOAKROOMOWNER_NAME_KEY, cloakRoomOwner.getName());
        values.put(Constants.CLOAKROOMOWNER_MAIL_KEY, cloakRoomOwner.getMail());
        values.put(Constants.CLOAKROOMOWNER_PROFILEPATH_KEY, cloakRoomOwner.getProfilePath());
        values.put(Constants.CLOAKROOMOWNER_CONTACT_KEY, cloakRoomOwner.getContact());
        values.put(Constants.CLOAKROOMOWNER_USERNAME_KEY, cloakRoomOwner.getUsername());
        values.put(Constants.CLOAKROOMOWNER_PASSWORD_KEY, cloakRoomOwner.getPassword());

        long ownerID = db.insert(Constants.CLOAKROOMOWNER_TABLE_NAME, null, values);

        db.close();
        return ownerID;
    }

    public long addCloakRoom(CloakRoom cloakRoom) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // Inserting Row to User table
        values.put(Constants.CLOAKROOM_NAME_KEY, cloakRoom.getName());
        values.put(Constants.CLOAKROOM_ADDRESS_KEY, cloakRoom.getAddress());
        values.put(Constants.CLOAKROOM_OPENING_TIME_KEY, cloakRoom.getOpensAt());
        values.put(Constants.CLOAKROOM_CONTACT_KEY, cloakRoom.getContact());
        values.put(Constants.CLOAKROOM_OWNER_ID_KEY, cloakRoom.getProfilePath());
        values.put(Constants.CLOAKROOM_CLOSING_TIME_KEY, cloakRoom.getClosesAt());
        values.put(Constants.CLOAKROOM_PER_HOUR_CHARGES_KEY, cloakRoom.getPerHourCharges());
        values.put(Constants.CLOAKROOM_OWNER_ID_KEY, cloakRoom.getOwnerID());
        values.put(Constants.CLOAKROOM_PROFILE_PATH_KEY, cloakRoom.getProfilePath());

        long cloakRoomID = db.insert(Constants.CLOAKROOM_TABLE_NAME, null, values);

        db.close();
        return cloakRoomID;
    }

    public long addFeedback(Feedback feedback) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // Inserting Row to User table
        values.put(Constants.FEEDBACK_DESCRIPTION_KEY, feedback.getDescription());
        values.put(Constants.TOURIST_ID_KEY, feedback.getTouristID());
        values.put(Constants.CLOAKROOM_ID_KEY, feedback.getCloakRoomID());
        values.put(Constants.LOCATION_ID_KEY, feedback.getLocationID());
        values.put(Constants.TOURIST_SPOT_ID_KEY, feedback.getTouristSpotID());

        long feedbackID = db.insert(Constants.FEEDBACK_TABLE_NAME, null, values);

        db.close();
        return feedbackID;
    }

    public ArrayList<CloakRoomOwner> getCloakRoomOwnerList() {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.CLOAKROOMOWNER_TABLE_NAME;

        String[] columns = new String[]{Constants.ID_KEY,
                Constants.CLOAKROOMOWNER_NAME_KEY,
                Constants.CLOAKROOMOWNER_USERNAME_KEY,
                Constants.CLOAKROOMOWNER_PASSWORD_KEY,

                Constants.CLOAKROOMOWNER_MAIL_KEY,
                Constants.CLOAKROOMOWNER_CONTACT_KEY,
                Constants.CLOAKROOMOWNER_PROFILEPATH_KEY};

        Cursor cursor = db.query(table, columns, null,
                null, null, null, null, null);
        ArrayList<CloakRoomOwner> cloakRoomOwnerList = new ArrayList();
        if (cursor.moveToFirst()) {
            do {

                long ownerID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));

                String name = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_NAME_KEY));
                String mail = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_MAIL_KEY));
                String contact = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_CONTACT_KEY));
                String profilePath = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_PROFILEPATH_KEY));
                String userName = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_USERNAME_KEY));
                String password = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_PASSWORD_KEY));


                CloakRoomOwner cloakRoomOwner = new CloakRoomOwner();
                cloakRoomOwner.setOwnerID(ownerID);
                cloakRoomOwner.setName(name);
                cloakRoomOwner.setContact(contact);
                cloakRoomOwner.setMail(mail);
                cloakRoomOwner.setUsername(userName);
                cloakRoomOwner.setPassword(password);
                cloakRoomOwner.setProfilePath(profilePath);
                cloakRoomOwnerList.add(cloakRoomOwner);
            } while (cursor.moveToNext());

        }

        db.close();
        return cloakRoomOwnerList;
    }

    public Feedback getFeedbackWithID(long feedbackID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.FEEDBACK_TABLE_NAME;
        String[] columns = new String[]{
                Constants.ID_KEY,
                Constants.CLOAKROOM_ID_KEY,
                Constants.LOCATION_ID_KEY,
                Constants.TOURIST_SPOT_ID_KEY,
                Constants.FEEDBACK_DESCRIPTION_KEY,
                Constants.TOURIST_ID_KEY};

        String where = Constants.ID_KEY + " =?";

        Cursor cursor = db.query(table, columns, where,
                new String[]{String.valueOf(feedbackID)}, null, null, null, null);

        Feedback feedback = null;
        if (cursor.moveToFirst()) {

            String description = cursor.getString(cursor.getColumnIndex(Constants.FEEDBACK_DESCRIPTION_KEY));
            long touristID = cursor.getLong(cursor.getColumnIndex(Constants.TOURIST_ID_KEY));
            long cloakRoomID = cursor.getLong(cursor.getColumnIndex(Constants.CLOAKROOM_ID_KEY));
            long locationID = cursor.getLong(cursor.getColumnIndex(Constants.LOCATION_ID_KEY));
            long touristSpotID = cursor.getLong(cursor.getColumnIndex(Constants.TOURIST_SPOT_ID_KEY));

            feedback = new Feedback();
            feedback.setFeedbackID(feedbackID);
            feedback.setDescription(description);
            feedback.setTouristID(touristID);
            feedback.setCloakRoomID(cloakRoomID);
            feedback.setLocationID(locationID);
            feedback.setTouristSpotID(touristSpotID);

        }

        db.close();
        return feedback;

    }

    public ArrayList<Feedback> getFeedbackList() {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.FEEDBACK_TABLE_NAME;
        String[] columns = new String[]{
                Constants.ID_KEY,
                Constants.FEEDBACK_DESCRIPTION_KEY,
                Constants.LOCATION_ID_KEY,
                Constants.TOURIST_SPOT_ID_KEY,
                Constants.TOURIST_ID_KEY};

        Cursor cursor = db.query(table, columns, null,
                null, null, null, null, null);

        ArrayList<Feedback> feedbackList = new ArrayList<Feedback>();
        if (cursor.moveToFirst()) {
            do {
                long feedbackID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));
                String description = cursor.getString(cursor.getColumnIndex(Constants.FEEDBACK_DESCRIPTION_KEY));
                long touristID = cursor.getLong(cursor.getColumnIndex(Constants.TOURIST_ID_KEY));
                long locationID = cursor.getLong(cursor.getColumnIndex(Constants.LOCATION_ID_KEY));
                long touristSpotID = cursor.getLong(cursor.getColumnIndex(Constants.TOURIST_SPOT_ID_KEY));

                Feedback feedback = new Feedback();
                feedback.setFeedbackID(feedbackID);
                feedback.setDescription(description);
                feedback.setTouristID(touristID);
                feedback.setLocationID(locationID);
                feedback.setTouristSpotID(touristSpotID);

                feedbackList.add(feedback);
            } while (cursor.moveToNext());
        }

        db.close();
        return feedbackList;
    }

    public ArrayList<String> getAllCloakRoomOwnerUserNames() {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.CLOAKROOMOWNER_TABLE_NAME;
        String[] columns = new String[]{Constants.CLOAKROOMOWNER_USERNAME_KEY};

        Cursor cursor = db.query(table, columns, null,
                null, null, null, null, null);

        ArrayList<String> cloakRoomOwnerUserNames = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_USERNAME_KEY));
                cloakRoomOwnerUserNames.add(username);
            } while (cursor.moveToNext());
        }

        db.close();
        return cloakRoomOwnerUserNames;
    }


    public CloakRoomOwner getCloakRoomOwnerWithUsernameAndPassword(String userName, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.CLOAKROOMOWNER_TABLE_NAME;

        String[] columns = new String[]{Constants.ID_KEY,
                Constants.CLOAKROOMOWNER_NAME_KEY,
                Constants.CLOAKROOMOWNER_MAIL_KEY,
                Constants.CLOAKROOMOWNER_CONTACT_KEY,
                Constants.CLOAKROOMOWNER_PROFILEPATH_KEY};
        String where = Constants.CLOAKROOMOWNER_USERNAME_KEY + " =?" + " AND " + Constants.CLOAKROOMOWNER_PASSWORD_KEY + " =?";

        Cursor cursor = db.query(table, columns, where,
                new String[]{userName, password}, null, null, null, null);
        CloakRoomOwner cloakRoomOwner = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            long ownerID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));

            String name = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_NAME_KEY));
            String mail = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_MAIL_KEY));
            String contact = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_CONTACT_KEY));
            String profilePath = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_PROFILEPATH_KEY));


            cloakRoomOwner = new CloakRoomOwner();
            cloakRoomOwner.setOwnerID(ownerID);
            cloakRoomOwner.setName(name);
            cloakRoomOwner.setContact(contact);
            cloakRoomOwner.setMail(mail);
            cloakRoomOwner.setUsername(userName);
            cloakRoomOwner.setPassword(password);
            cloakRoomOwner.setProfilePath(profilePath);

        }

        db.close();
        return cloakRoomOwner;
    }

    public CloakRoomOwner getCloakRoomOwnerWithUserName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.CLOAKROOMOWNER_TABLE_NAME;

        String[] columns = new String[]{Constants.ID_KEY,
                Constants.CLOAKROOMOWNER_NAME_KEY,
                Constants.CLOAKROOMOWNER_MAIL_KEY,
                Constants.CLOAKROOMOWNER_PASSWORD_KEY,
                Constants.CLOAKROOMOWNER_CONTACT_KEY,
                Constants.CLOAKROOMOWNER_PROFILEPATH_KEY};
        String where = Constants.CLOAKROOMOWNER_USERNAME_KEY + " =?";

        Cursor cursor = db.query(table, columns, where,
                new String[]{username}, null, null, null, null);
        CloakRoomOwner cloakRoomOwner = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            long ownerID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));

            String name = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_NAME_KEY));
            String password = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_PASSWORD_KEY));

            String mail = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_MAIL_KEY));
            String contact = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_CONTACT_KEY));
            String profilePath = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_PROFILEPATH_KEY));


            cloakRoomOwner = new CloakRoomOwner();
            cloakRoomOwner.setOwnerID(ownerID);
            cloakRoomOwner.setName(name);
            cloakRoomOwner.setContact(contact);
            cloakRoomOwner.setMail(mail);
            cloakRoomOwner.setUsername(username);
            cloakRoomOwner.setPassword(password);
            cloakRoomOwner.setProfilePath(profilePath);

        }

        db.close();
        return cloakRoomOwner;

    }

    public CloakRoomOwner getCloakRoomOwnerWithID(long cloakRoomOwnerID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.CLOAKROOMOWNER_TABLE_NAME;

        String[] columns = new String[]{
                Constants.CLOAKROOMOWNER_NAME_KEY,
                Constants.CLOAKROOMOWNER_MAIL_KEY,
                Constants.CLOAKROOMOWNER_PASSWORD_KEY,
                Constants.CLOAKROOMOWNER_USERNAME_KEY,

                Constants.CLOAKROOMOWNER_CONTACT_KEY,
                Constants.CLOAKROOMOWNER_PROFILEPATH_KEY};
        String where = Constants.ID_KEY + " =?";

        Cursor cursor = db.query(table, columns, where,
                new String[]{String.valueOf(cloakRoomOwnerID)}, null, null, null, null);
        CloakRoomOwner cloakRoomOwner = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            String name = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_NAME_KEY));
            String password = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_PASSWORD_KEY));
            String username = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_USERNAME_KEY));

            String mail = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_MAIL_KEY));
            String contact = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_CONTACT_KEY));
            String profilePath = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOMOWNER_PROFILEPATH_KEY));


            cloakRoomOwner = new CloakRoomOwner();
            cloakRoomOwner.setOwnerID(cloakRoomOwnerID);
            cloakRoomOwner.setName(name);
            cloakRoomOwner.setContact(contact);
            cloakRoomOwner.setMail(mail);
            cloakRoomOwner.setUsername(username);
            cloakRoomOwner.setPassword(password);
            cloakRoomOwner.setProfilePath(profilePath);
        }
        db.close();
        return cloakRoomOwner;
    }

    public ArrayList<CloakRoom> getCloakRoomList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String table = Constants.CLOAKROOM_TABLE_NAME;
        String[] columns = new String[]{Constants.ID_KEY,
                Constants.CLOAKROOM_NAME_KEY,
                Constants.CLOAKROOM_CONTACT_KEY,
                Constants.CLOAKROOM_OPENING_TIME_KEY,
                Constants.CLOAKROOM_ADDRESS_KEY,
                Constants.CLOAKROOM_CLOSING_TIME_KEY,
                Constants.CLOAKROOM_PER_HOUR_CHARGES_KEY,
                Constants.CLOAKROOM_PROFILE_PATH_KEY,
                Constants.CLOAKROOM_OWNER_ID_KEY
        };

        Cursor cursor = db.query(table, columns, null,
                null, null, null, null, null);
        ArrayList<CloakRoom> cloakRoomList = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                long cloakRoomID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));

                long ownerID = cursor.getLong(cursor.getColumnIndex(Constants.CLOAKROOM_OWNER_ID_KEY));

                String name = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOM_NAME_KEY));
                String profilePath = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOM_PROFILE_PATH_KEY));
                String address = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOM_ADDRESS_KEY));
                String contact = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOM_CONTACT_KEY));
                String openingTime = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOM_OPENING_TIME_KEY));
                String closingTime = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOM_CLOSING_TIME_KEY));
                String perHourCharges = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOM_PER_HOUR_CHARGES_KEY));

                CloakRoom cloakRoom = new CloakRoom();
                cloakRoom.setCloakRoomID(cloakRoomID);
                cloakRoom.setName(name);
                cloakRoom.setAddress(address);
                cloakRoom.setContact(contact);
                cloakRoom.setOpensAt(openingTime);
                cloakRoom.setClosesAt(closingTime);
                cloakRoom.setPerHourCharges(perHourCharges);
                cloakRoom.setOwnerID(ownerID);


                cloakRoom.setProfilePath(profilePath);
                cloakRoomList.add(cloakRoom);

            } while (cursor.moveToNext());

        }

        db.close();
        return cloakRoomList;
    }


    public CloakRoom getCloakRoomWithID(long cloakRoomID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String table = Constants.CLOAKROOM_TABLE_NAME;
        String[] columns = new String[]{Constants.ID_KEY,
                Constants.CLOAKROOM_NAME_KEY,
                Constants.CLOAKROOM_CONTACT_KEY,
                Constants.CLOAKROOM_OPENING_TIME_KEY,
                Constants.CLOAKROOM_ADDRESS_KEY,
                Constants.CLOAKROOM_CLOSING_TIME_KEY,
                Constants.CLOAKROOM_PER_HOUR_CHARGES_KEY,
                Constants.CLOAKROOM_PROFILE_PATH_KEY,
                Constants.CLOAKROOM_OWNER_ID_KEY
        };
        String where = Constants.ID_KEY + " =?";

        Cursor cursor = db.query(table, columns, where,
                new String[]{String.valueOf(cloakRoomID)}, null, null, null, null);
        CloakRoom cloakRoom = null;
        if (cursor.moveToFirst()) {
            long ownerID = cursor.getLong(cursor.getColumnIndex(Constants.CLOAKROOM_OWNER_ID_KEY));

            String name = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOM_NAME_KEY));
            String profilePath = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOM_PROFILE_PATH_KEY));
            String address = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOM_ADDRESS_KEY));
            String contact = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOM_CONTACT_KEY));
            String openingTime = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOM_OPENING_TIME_KEY));
            String closingTime = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOM_CLOSING_TIME_KEY));
            String perHourCharges = cursor.getString(cursor.getColumnIndex(Constants.CLOAKROOM_PER_HOUR_CHARGES_KEY));

            cloakRoom = new CloakRoom();
            cloakRoom.setCloakRoomID(cloakRoomID);
            cloakRoom.setName(name);
            cloakRoom.setAddress(address);
            cloakRoom.setContact(contact);
            cloakRoom.setOpensAt(openingTime);
            cloakRoom.setClosesAt(closingTime);
            cloakRoom.setPerHourCharges(perHourCharges);
            cloakRoom.setOwnerID(ownerID);


            cloakRoom.setProfilePath(profilePath);
        }
        db.close();
        return cloakRoom;
    }

    public int updatePasswordForCloakRoomOwner(String newPassword, long cloakRoomOwnerID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.CLOAKROOMOWNER_PASSWORD_KEY, newPassword);

        // Updating row
        return db.update(Constants.CLOAKROOMOWNER_TABLE_NAME, values, Constants.ID_KEY + "=?",
                new String[]{String.valueOf(cloakRoomOwnerID)});
    }

    public int updatePasswordForTourist(String newPassword, long touristID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.TOURIST_PASSWORD_KEY, newPassword);

        // Updating row
        return db.update(Constants.TOURIST_TABLE_NAME, values, Constants.ID_KEY + "=?",
                new String[]{String.valueOf(touristID)});
    }


    public ArrayList<String> getAllTouristUserNames() {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.TOURIST_TABLE_NAME;
        String[] columns = new String[]{Constants.TOURIST_USERNAME_KEY};

        Cursor cursor = db.query(table, columns, null,
                null, null, null, null, null);

        ArrayList<String> touristUserNames = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_USERNAME_KEY));
                touristUserNames.add(username);
            } while (cursor.moveToNext());
        }

        db.close();
        return touristUserNames;
    }


    public Tourist getTouristWithUsernameAndPassword(String userName, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.TOURIST_TABLE_NAME;

        String[] columns = new String[]{Constants.ID_KEY,
                Constants.TOURIST_NAME_KEY,
                Constants.TOURIST_MAIL_KEY,
                Constants.TOURIST_CONTACT_KEY,
                Constants.TOURIST_PROFILEPATH_KEY};
        String where = Constants.TOURIST_USERNAME_KEY + " =?" + " AND " + Constants.TOURIST_PASSWORD_KEY + " =?";

        Cursor cursor = db.query(table, columns, where,
                new String[]{userName, password}, null, null, null, null);
        Tourist tourist = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            long touristID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));

            String name = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_NAME_KEY));
            String mail = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_MAIL_KEY));
            String contact = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_CONTACT_KEY));
            String profilePath = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_PROFILEPATH_KEY));


            tourist = new Tourist();
            tourist.setTouristID(touristID);
            tourist.setName(name);
            tourist.setContact(contact);
            tourist.setMail(mail);
            tourist.setUsername(userName);
            tourist.setPassword(password);
            tourist.setProfilePath(profilePath);

        }

        db.close();
        return tourist;
    }

    public Tourist getTouristWithUsername(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.TOURIST_TABLE_NAME;

        String[] columns = new String[]{Constants.ID_KEY,
                Constants.TOURIST_NAME_KEY,
                Constants.TOURIST_PASSWORD_KEY,
                Constants.TOURIST_MAIL_KEY,
                Constants.TOURIST_CONTACT_KEY,
                Constants.TOURIST_PROFILEPATH_KEY};
        String where = Constants.TOURIST_USERNAME_KEY + " =?";

        Cursor cursor = db.query(table, columns, where,
                new String[]{userName}, null, null, null, null);
        Tourist tourist = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            long touristID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));

            String name = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_NAME_KEY));
            String password = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_PASSWORD_KEY));

            String mail = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_MAIL_KEY));
            String contact = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_CONTACT_KEY));
            String profilePath = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_PROFILEPATH_KEY));
            tourist = new Tourist();
            tourist.setTouristID(touristID);
            tourist.setName(name);
            tourist.setContact(contact);
            tourist.setMail(mail);
            tourist.setUsername(userName);
            tourist.setPassword(password);
            tourist.setProfilePath(profilePath);
        }

        db.close();
        return tourist;
    }


    public Tourist getTouristWithID(long touristID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.TOURIST_TABLE_NAME;

        String[] columns = new String[]{
                Constants.TOURIST_NAME_KEY,
                Constants.TOURIST_MAIL_KEY,
                Constants.TOURIST_PASSWORD_KEY,
                Constants.TOURIST_USERNAME_KEY,
                Constants.TOURIST_CONTACT_KEY,
                Constants.TOURIST_PROFILEPATH_KEY};
        String where = Constants.ID_KEY + " =?";

        Cursor cursor = db.query(table, columns, where,
                new String[]{String.valueOf(touristID)}, null, null, null, null);
        Tourist tourist = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            String name = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_NAME_KEY));
            String password = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_PASSWORD_KEY));
            String username = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_USERNAME_KEY));
            String mail = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_MAIL_KEY));
            String contact = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_CONTACT_KEY));
            String profilePath = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_PROFILEPATH_KEY));

            tourist = new Tourist();
            tourist.setTouristID(touristID);
            tourist.setName(name);
            tourist.setContact(contact);
            tourist.setMail(mail);
            tourist.setUsername(username);
            tourist.setPassword(password);
            tourist.setProfilePath(profilePath);
        }
        db.close();
        return tourist;
    }

    public ArrayList<Tourist> getTouristList() {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.TOURIST_TABLE_NAME;

        String[] columns = new String[]{
                Constants.TOURIST_NAME_KEY,
                Constants.ID_KEY,
                Constants.TOURIST_MAIL_KEY,
                Constants.TOURIST_PASSWORD_KEY,
                Constants.TOURIST_USERNAME_KEY,
                Constants.TOURIST_CONTACT_KEY,
                Constants.TOURIST_PROFILEPATH_KEY};

        Cursor cursor = db.query(table, columns, null,
                null, null, null, null, null);
        ArrayList<Tourist> touristList = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                Tourist tourist = null;

                    String name = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_NAME_KEY));
                    String password = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_PASSWORD_KEY));
                    String username = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_USERNAME_KEY));
                    String mail = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_MAIL_KEY));
                    String contact = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_CONTACT_KEY));
                    String profilePath = cursor.getString(cursor.getColumnIndex(Constants.TOURIST_PROFILEPATH_KEY));
                    long touristID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));

                    tourist = new Tourist();
                    tourist.setTouristID(touristID);
                    tourist.setName(name);
                    tourist.setContact(contact);
                    tourist.setMail(mail);
                    tourist.setUsername(username);
                    tourist.setPassword(password);
                    tourist.setProfilePath(profilePath);
                    touristList.add(tourist);



            }                while (cursor.moveToNext()) ;


        }
        db.close();
        return touristList;


    }

    public ArrayList<Booking> getAllBookingsForTouristWithID(long touristID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Booking> bookings = new ArrayList();

        String table = Constants.BOOKING_TABLE_NAME;
        String[] tableColumns = new String[]{
                Constants.ID_KEY,
                Constants.BOOKING_DATE_KEY,
                Constants.TOURIST_ID_KEY,
                Constants.CLOAKROOM_ID_KEY};
        String where_clause =  Constants.TOURIST_ID_KEY + " =?";

        Cursor cursor = db.query(table, tableColumns, where_clause,
                new String[]{String.valueOf(touristID)}, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do
            {
                long bookingID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));
                long cloakRoomID = cursor.getLong(cursor.getColumnIndex(Constants.CLOAKROOM_ID_KEY));

                String dateString = cursor.getString(cursor.getColumnIndex(Constants.BOOKING_DATE_KEY));

                Calendar newDate = Utility.stringToDate(dateString);

                Booking booking = new Booking();
                booking.setBookingID(bookingID);
                booking.setCloakRoomID(cloakRoomID);
                booking.setTouristID(touristID);
                booking.setDate(newDate);

                bookings.add(booking);
            } while (cursor.moveToNext());
        }
        db.close();
        return bookings;
    }

    public long addBooking(Booking booking)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // Inserting Row to User table
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String date = dateFormatter.format(booking.getDate().getTime());

        values.put(Constants.BOOKING_DATE_KEY, date);
        values.put(Constants.TOURIST_ID_KEY, booking.getTouristID());
        values.put(Constants.CLOAKROOM_ID_KEY, booking.getCloakRoomID());
        values.put(Constants.OTP_KEY, booking.getOtp());

        long bookingID = db.insert(Constants.BOOKING_TABLE_NAME, null, values);

        db.close();
        return bookingID;
    }

    public Booking getBookingWithID(long bookingID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String table = Constants.BOOKING_TABLE_NAME;
        String[] tableColumns = new String[]{
                Constants.BOOKING_DATE_KEY,
                Constants.TOURIST_ID_KEY,
                Constants.OTP_KEY,
                Constants.CLOAKROOM_ID_KEY
                };

        String where_clause =  Constants.ID_KEY + " =?";

        Cursor cursor = db.query(table, tableColumns, where_clause,
                new String[]{String.valueOf(bookingID)}, null, null, null, null);

        Booking booking = null;
        if (cursor.moveToFirst())
        {

            long cloakRoomID = cursor.getLong(cursor.getColumnIndex(Constants.CLOAKROOM_ID_KEY));
            long touristID = cursor.getLong(cursor.getColumnIndex(Constants.TOURIST_ID_KEY));
            int otp = cursor.getInt(cursor.getColumnIndex(Constants.OTP_KEY));

            String dateString = cursor.getString(cursor.getColumnIndex(Constants.BOOKING_DATE_KEY));

            Calendar newDate = Utility.stringToDate(dateString);

            booking = new Booking();
            booking.setBookingID(bookingID);
            booking.setTouristID(touristID);
            booking.setCloakRoomID(cloakRoomID);
            booking.setOtp(otp);
            booking.setDate(newDate);
        }
        db.close();
        return booking;

    }

    public void deleteBookingWithID(long bookingID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.BOOKING_TABLE_NAME, Constants.ID_KEY + "=?",
                new String[]{String.valueOf(bookingID)});
        db.close();
    }

    public int updateDetailsForTourist(Tourist tourist){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.TOURIST_MAIL_KEY , tourist.getMail());
        values.put(Constants.TOURIST_CONTACT_KEY , tourist.getContact());
        values.put(Constants.TOURIST_PROFILEPATH_KEY , tourist.getProfilePath());

        // Updating row
        return db.update(Constants.TOURIST_TABLE_NAME, values, Constants.ID_KEY + "=?",
                new String[]{String.valueOf(tourist.getTouristID())});
    }

    public int updateOTPForBooking(Booking booking)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.OTP_KEY, booking.getOtp());

        // Updating row
        return db.update(Constants.BOOKING_TABLE_NAME, values, Constants.ID_KEY + "=?",
                new String[]{String.valueOf(booking.getBookingID())});

    }



    public ArrayList<Booking> getAllBookings()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Booking> bookings = new ArrayList();

        String table = Constants.BOOKING_TABLE_NAME;
        String[] tableColumns = new String[]{
                Constants.ID_KEY,
                Constants.BOOKING_DATE_KEY,
                Constants.TOURIST_ID_KEY,
                Constants.CLOAKROOM_ID_KEY};

        Cursor cursor = db.query(table, tableColumns, null,
                null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do
            {
                long bookingID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));
                long cloakRoomID = cursor.getLong(cursor.getColumnIndex(Constants.CLOAKROOM_ID_KEY));
                long touristID = cursor.getLong(cursor.getColumnIndex(Constants.TOURIST_ID_KEY));

                String dateString = cursor.getString(cursor.getColumnIndex(Constants.BOOKING_DATE_KEY));

                Calendar newDate = Utility.stringToDate(dateString);

                Booking booking = new Booking();
                booking.setBookingID(bookingID);
                booking.setCloakRoomID(cloakRoomID);
                booking.setTouristID(touristID);
                booking.setDate(newDate);

                bookings.add(booking);
            } while (cursor.moveToNext());
        }
        db.close();
        return bookings;
    }

    public int updateDetailsForOwner(CloakRoomOwner owner)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.CLOAKROOMOWNER_MAIL_KEY , owner.getMail());
        values.put(Constants.CLOAKROOMOWNER_CONTACT_KEY , owner.getContact());
        values.put(Constants.CLOAKROOMOWNER_PASSWORD_KEY , owner.getPassword());
        values.put(Constants.CLOAKROOMOWNER_PROFILEPATH_KEY,owner.getProfilePath());

        // Updating row
        return db.update(Constants.CLOAKROOMOWNER_TABLE_NAME, values, Constants.ID_KEY + "=?",
                new String[]{String.valueOf(owner.getOwnerID())});
    }

}