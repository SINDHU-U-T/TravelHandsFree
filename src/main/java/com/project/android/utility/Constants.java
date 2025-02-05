package com.project.android.utility;

public class Constants { // Error Messages
    public static final String MISSING_NAME = "Please enter name";
    public static final String MISSING_PASSWORD = "Please enter your password";
    public static final String INVALID_ADMIN_CREDENTIALS = "Wrong Credentials";
    public static final String MISSING_PASSWORD_CONFIRMATION = "Please confirm your password";
    public static final String PASSWORD_MISMATCH = "Password does not match";
    public static final String INVALID_MOBILE = "Mobile number should be 10 digit number";
    public static final String MISSING_MAIL = "Please enter email address";
    public static final String INVALID_MAIL = "Invalid email address";

    // App Description
    public static final String APP_DESCRIPTION = "This App give travelers who don’t have alternative options a way to safely stow their luggage so that they don’t have to carry it with them. With this app you can have your bags only when you want them – this app  will take care of them when you don’t. This is Luggage storage network that connects travelers with local businesses (shops, cafes, hotels etc.) across the country. Our platform allows travelers to leave their bags/boxes with a local shop near them and enjoy the city without having to lug around their heavy bags when all they should be doing is living hands free life.";

    // Database version
    public static final int DATABASE_VERSION = 1;

    // Database name
    public static final String DATABASE_NAME = "TravelHandsFree";


    // Common column names
    public static final String ID_KEY = "_id";


    public static final String DRAWABLE_RESOURCE = "drawable";


    //Cloak Room Owner table
    public static final String CLOAKROOMOWNER_TABLE_NAME = "CloakRoomOwner";
    public static final String CLOAKROOMOWNER_NAME_KEY = "Name";
    public static final String CLOAKROOMOWNER_PASSWORD_KEY = "Password";
    public static final String CLOAKROOMOWNER_MAIL_KEY = "Mail";
    public static final String CLOAKROOMOWNER_CONTACT_KEY = "Contact";
    public static final String CLOAKROOMOWNER_PROFILEPATH_KEY = "ProfilePath";
    public static final String CLOAKROOMOWNER_USERNAME_KEY = "Username";

    //Cloak Room table
    public static final String CLOAKROOM_TABLE_NAME = "CloakRoom";
    public static final String CLOAKROOM_NAME_KEY = "Name";
    public static final String CLOAKROOM_ADDRESS_KEY = "Address";
    public static final String CLOAKROOM_CONTACT_KEY = "Contact";
    public static final String CLOAKROOM_OPENING_TIME_KEY = "OpensAt";
    public static final String CLOAKROOM_CLOSING_TIME_KEY = "ClosesAt";
    public static final String CLOAKROOM_PER_HOUR_CHARGES_KEY = "PerHourCharges";
    public static final String CLOAKROOM_OWNER_ID_KEY = "OwnerID";
    public static final String CLOAKROOM_PROFILE_PATH_KEY = "ProfilePath";

    //Tourist Table
    public static final String TOURIST_TABLE_NAME = "Tourist";
    public static final String TOURIST_NAME_KEY = "Name";
    public static final String TOURIST_PASSWORD_KEY = "Password";
    public static final String TOURIST_MAIL_KEY = "Mail";
    public static final String TOURIST_CONTACT_KEY = "Contact";
    public static final String TOURIST_USERNAME_KEY = "Username";
    public static final String TOURIST_PROFILEPATH_KEY = "ProfilePath";

    // Booking Table
    public static final String BOOKING_TABLE_NAME = "Bookings";
    public static final String BOOKING_DATE_KEY = "Date";
    public static final String TOURIST_ID_KEY = "TouristID";
    public static final String CLOAKROOM_ID_KEY = "CloakRoomID";
    public static final String OTP_KEY = "OTP";

    public static final String MISSING_FEEDBACK_DESCRIPTION = "Please enter feedback";
    public static final String FEEDBACK_POSTED_SUCCESSSFULLY = "Feedback is posted successfully";
    //Feedback Table
    public static final String FEEDBACK_TABLE_NAME = "Feedback";
    public static final String FEEDBACK_DESCRIPTION_KEY = "Description";
    public static final String LOCATION_ID_KEY = "LocationID";
    public static final String TOURIST_SPOT_ID_KEY = "TouristSpotID";

    public static final String NOFEEDBACK_DESCRIPTION = "There is no feedback by any of the tourists";
    public static final String NOBOOKINGS_FOR_TOURIST_DESCRIPTION = "There are no bookings for tourist ";
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    public static final int MY_CAMERA_REQUEST_CODE = 143;

    public static final int APPROVE_REQUEST_SEND_SMS = 1;
    public static final int REJECT_REQUEST_SEND_SMS = 2;

    public static final String FORGOT_PASSWORD = "The registered password has been sent to your registered mobile number";
    public static final String INVALID_CREDENTIALS = "Wrong Credentials";
    public static final String CANNOT_CANCEL_BOOKING = "You have already availed this booking. It cannot be cancelled";
    public static final String BOOKING_CANCELLED_SUCCESSFULLY = "This booking is cancelled successfully";

    public static final String THIS_BOOKING_IS_AVAILED = "You have already availed this booking. It cannot be cancelled";


    public static final String MISSING_MOBILE = "Please enter your Mbl no";
    public static final String MISSING_USERNAME = "Please enter username";
    public static final String INVALID_EMAIL = "Please enter valid Email";
    public static final String MISSING_EMAIL = "Please enter your  Mail";
    public static final String INVALID_PASSWORD = "Please enter valid password";
    public static final int MINIMUM_PASSWORD_LENGTH = 8;
    public static final int REQUEST_CAMERA = 123;
    public static final int SELECT_FILE = 234;

    public static final String ADMIN_PASSWORD = "admin";
    public static final String ADMIN_PASSWORD_KEY = "password";
    public static final String ADMIN_MOBILE = "";


    public static final String FEEDBACK_SUBJECT = "Travel Hands Free App";
    public static final String FEEDBACK_MAILID = "travelhandsfreefeedback@gmail.com";
    public static final String HELP_MESSAGE = "Please mail to \ntravelhandsfreefeedback@gmail.com\nfor any help regarding the app.";


    public static final String MISSING_OLD_PASSWORD = "Please enter your old password";
    public static final String OLD_PASSWORD_INCORRECT = "Your old password is incorrect";
    public static final String MISSING_NEW_PASSWORD = "Please enter your new password";
    public static final String PASSWORD_CHANGED_SUCCESSFULLY = "Password is changed successfully";

    public static final String MISSING_ADDRESS = "Please enter your address";


    public static final String DUPLICATE_USERNAME = "Username already exists. Please select a different username";
    public static final String UNREGISTERED_TOURIST = "Tourist is not registered in the app. You cannot continue";
    public static final String UNREGISTERED_CLOAKROOMOWNER = "Cloak Room Owner is not registered in the app. You cannot continue";
    public static final String MISSING_VISITOR_PROFILEPHOTO = "Please enter profile photo";


    public static final String CLOAKROOMOWNER_REGISTERED_SUCCESSFULLY = "Cloak room owner is registered successfully";
    public static final String TOURIST_REGISTERED_SUCCESSFULLY = "Tourist is registered successfully";
    public static final String CLOAKROOM_ADDED_SUCCESSFULLY = "Cloak room is added successfully";
    public static final String MISSING_PER_HOUR_CHARGES = "Please enter per hour charges";
    public static final String MISSING_CLOAK_ROOM_PHOTO = "Please select Cloak Room photo";
    public static final String MISSING_TOURIST_PROFILEPHOTO = "Please select Tourist profile photo";
    public static final String NOTOURIST_DESCRIPTION = "Currently there are no tourists";
    public static final String NOCLOAKROOM_DESCRIPTION = "Currently no cloak rooms are added";
    public static final String MISSING_DATE = "Please select a date";
    public static final String BOOKING_PROGRESS = "Booking being processed... Please wait.";
    public static final String TOURIST_EDITED_SUCCESSFULLY = "Tourist details are edited successfully";
    public static final String RUPEE_SYMBOL = "\u20B9";

    public static final String NOLOCATION_DESCRIPTION = "Admin has not added any locations";
    public static final String NOTOURIST_SPOT__DESCRIPTION = "There are no tourist spots for this location";

    public static final String MISSING_BOOKING_ID = "Please enter booking ID";
    public static final String BOOKING_NOT_FOUND = "Booking with this id does not exist";
    public static final String MISSING_OTP = "Please enter OTP sent during luggage submission";
    public static final String THIS_OTP_IS_INVALID = "OTP is invalid";
    public static final String NOT_APPLICABLE = "N/A";
    public static final String NOBOOKINGS_DESCRIPTION = "Currently there are no bookings";
    public static final String DETAILS_EDITED_SUCCESSFULLY = "Details are edited successfully";
    public static final String MISSING_PROFILEPHOTO = "Please select a profile photo";
    public static final String CANNOT_GIVE_FEEDBACK = "There are no cloak rooms to give feedback";
}
