package com.project.android.activitycontrollers.tourist;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Booking;
import com.project.android.model.CloakRoom;
import com.project.android.model.Tourist;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;
import com.project.android.utility.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookingDetailsActivity extends AppCompatActivity
{
    private TextView dateTV, cloakRoomTV, touristTV;
    private Booking booking = null;
    private CloakRoom cloakRoom = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingdetails);
        initializeUIComponents();

        Bundle bundle = this.getIntent().getExtras();

        long bookingID = bundle.getLong(Constants.ID_KEY);
        AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
        booking = databaseHelper.getBookingWithID(bookingID);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String travelDateString = dateFormatter.format(booking.getDate().getTime());
        dateTV.setText(travelDateString);
        cloakRoom = databaseHelper.getCloakRoomWithID(booking.getCloakRoomID());
        cloakRoomTV.setText(cloakRoom.getName());
        Tourist tourist = ((AppInstance)getApplicationContext()).getCurrentTourist();
        touristTV.setText(tourist.getName());

    }

    public void initializeUIComponents() {
        cloakRoomTV = findViewById(R.id.cloakRoom);
        dateTV = findViewById(R.id.date);
        touristTV =  findViewById(R.id.tourist);
    }

    public void cancelBooking(View view)
    {
        Calendar calendar = Calendar.getInstance();

        Date today = calendar.getTime();

        Date dateOfTravel = null;
        if (null == dateOfTravel)
        {
            dateOfTravel = booking.getDate().getTime();
        }

//        String[] timeArray= booking.getBookingTime().split(":");
//        int hourSelected = Integer.parseInt(timeArray[0]);
//        String[] minArray = timeArray[1].split(" ");
//
//        int minuteSelected = Integer.parseInt(minArray[0]);

        if ((Utility.getZeroTimeDate(dateOfTravel).compareTo(Utility.getZeroTimeDate(today))) < 0) {
            Toast.makeText(getApplicationContext(), Constants.CANNOT_CANCEL_BOOKING, Toast.LENGTH_LONG).show();
        }
        else if ((Utility.getZeroTimeDate(dateOfTravel).compareTo(Utility.getZeroTimeDate(today))) == 0) {
//            if ((hourSelected < nowHour || (hourSelected == nowHour && minuteSelected <= nowMinute)))
//            {
                Toast.makeText(getApplicationContext(), Constants.CANNOT_CANCEL_BOOKING, Toast.LENGTH_LONG).show();
//            }
//            else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

                // Setting Dialog Title
                alertDialog.setTitle("Cancel Booking...");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want to cancel this booking?");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.delete);
                alertDialog.setCancelable(false);
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                AppDatabaseHelper databaseHelper = new AppDatabaseHelper(BookingDetailsActivity.this);
                                databaseHelper.deleteBookingWithID(booking.getBookingID());
                                Toast.makeText(getApplicationContext(), Constants.BOOKING_CANCELLED_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(BookingDetailsActivity.this, TouristHomeActivity.class);
                                startActivity(i);
                                finish();
                                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                        Manifest.permission.SEND_SMS)
                                        != PackageManager.PERMISSION_GRANTED)
                                {

                                    // Permission is not granted
                                    // Should we show an explanation?
                                    if (ActivityCompat.shouldShowRequestPermissionRationale(BookingDetailsActivity.this,
                                            Manifest.permission.SEND_SMS))
                                    {

                                        // Show an explanation to the user *asynchronously* -- don't block
                                        // this thread waiting for the tourist's response! After the tourist
                                        // sees the explanation, try again to request the permission.

                                    }
                                    else
                                    {

                                        // No explanation needed; request the permission
                                        ActivityCompat.requestPermissions(BookingDetailsActivity.this,
                                                new String[]{Manifest.permission.SEND_SMS},
                                                Constants.MY_PERMISSIONS_REQUEST_SEND_SMS);

                                        // MY_PERMISSIONS_REQUEST_SEND_SMS is an
                                        // app-defined int constant. The callback method gets the
                                        // result of the request.
                                    }
                                }
                                else
                                {
                                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                                    String date = dateFormatter.format(booking.getDate().getTime());

                                    Tourist tourist = ((AppInstance)getApplicationContext()).getCurrentTourist();
                                    sendSms(cloakRoom.getContact(),  "Booking with the below details is cancelled by Tourist using Android App.\n" + "Booking ID: " + booking.getBookingID() +  "\nTourist: " + tourist.getName() + "\nDate: " + date );
                                    sendSms(tourist.getContact(),  "You have cancelled the following booking using Android App.\n" + "Booking ID: " + booking.getBookingID() + "\nCloak Room: " + cloakRoom.getName() + "\nDate: " + date );
                                    // Permission has already been granted
                                }
                            }

                        });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });

                // Showing Alert Message
                alertDialog.show();

            }
//        }
        else
        {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting Dialog Title
            alertDialog.setTitle("Cancel Booking...");

            // Setting Dialog Message
            alertDialog.setMessage("Are you sure you want to cancel this booking?");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.cloakroom);
            alertDialog.setCancelable(false);
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            AppDatabaseHelper databaseHelper = new AppDatabaseHelper(BookingDetailsActivity.this);
                            databaseHelper.deleteBookingWithID(booking.getBookingID());
                            Toast.makeText(getApplicationContext(), Constants.BOOKING_CANCELLED_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(BookingDetailsActivity.this, TouristHomeActivity.class);
                            startActivity(i);
                            finish();
                            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                    Manifest.permission.SEND_SMS)
                                    != PackageManager.PERMISSION_GRANTED)
                            {

                                // Permission is not granted
                                // Should we show an explanation?
                                if (ActivityCompat.shouldShowRequestPermissionRationale(BookingDetailsActivity.this,
                                        Manifest.permission.SEND_SMS))
                                {

                                    // Show an explanation to the user *asynchronously* -- don't block
                                    // this thread waiting for the tourist's response! After the tourist
                                    // sees the explanation, try again to request the permission.

                                }
                                else
                                {

                                    // No explanation needed; request the permission
                                    ActivityCompat.requestPermissions(BookingDetailsActivity.this,
                                            new String[]{Manifest.permission.SEND_SMS},
                                            Constants.MY_PERMISSIONS_REQUEST_SEND_SMS);

                                    // MY_PERMISSIONS_REQUEST_SEND_SMS is an
                                    // app-defined int constant. The callback method gets the
                                    // result of the request.
                                }
                            }
                            else
                            {
                                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                                String date = dateFormatter.format(booking.getDate().getTime());

                                Tourist tourist = ((AppInstance)getApplicationContext()).getCurrentTourist();
                                sendSms(cloakRoom.getContact(),  "Booking with the below details is cancelled by Tourist using Android App.\n" + "Booking ID: " + booking.getBookingID() +  "\nTourist: " + tourist.getName() + "\nDate: " + date );
                                sendSms(tourist.getContact(),  "You have cancelled the following booking using Android App.\n" + "Booking ID: " + booking.getBookingID() + "\nCloak Room: " + cloakRoom.getName() + "\nDate: " + date );
                                // Permission has already been granted
                            }

                        }
                    });
            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            dialog.cancel();
                        }
                    });

            // Showing Alert Message
            alertDialog.show();

        }
    }
    private void sendSms(String phonenumber, String message)
    {
        try {

            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> msgArray = smsManager.divideMessage(message);

            smsManager.sendMultipartTextMessage(phonenumber, null,msgArray, null, null);
            //Toast.makeText(getApplicationContext(), "Message Sent",Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sub_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                ((AppInstance) getApplicationContext()).setCurrentTourist(null);
                Intent i = new Intent(this, UserSelectionActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.about:
                android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
                dialogBuilder.setIcon(R.drawable.logo);
                dialogBuilder.setTitle(R.string.app_name);
                dialogBuilder.setMessage(Constants.APP_DESCRIPTION);
                dialogBuilder.create();
                dialogBuilder.show();
                return true;
            case R.id.changePassword:
                Intent intent = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent);
                return true;
            case R.id.help:
                android.app.AlertDialog.Builder helpDialogBuilder = new android.app.AlertDialog.Builder(this);
                helpDialogBuilder.setIcon(R.drawable.logo);
                helpDialogBuilder.setTitle(R.string.app_name);
                helpDialogBuilder.setMessage(Constants.HELP_MESSAGE);
                helpDialogBuilder.create();
                helpDialogBuilder.show();
                return true;
            case R.id.feedback:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, Constants.FEEDBACK_SUBJECT);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{Constants.FEEDBACK_MAILID});

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send complaint..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_LONG).show();
                }
                return true;

        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_SEND_SMS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AppDatabaseHelper databaseHelper = new AppDatabaseHelper(BookingDetailsActivity.this);

                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    String date = dateFormatter.format(booking.getDate().getTime());

                    Tourist tourist = ((AppInstance)getApplicationContext()).getCurrentTourist();
                    sendSms(cloakRoom.getContact(),  "Booking with the below details is cancelled by Tourist using Android App.\n" + "Booking ID: " + booking.getBookingID() +  "\nTourist: " + tourist.getName() + "\nDate: " + date );
                    sendSms(tourist.getContact(),  "You have cancelled the following booking using Android App.\n" + "Booking ID: " + booking.getBookingID() + "\nCloak Room: " + cloakRoom.getName() + "\nDate: " + date );

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
        }
    }

}
