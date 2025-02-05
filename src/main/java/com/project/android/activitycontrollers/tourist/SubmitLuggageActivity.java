package com.project.android.activitycontrollers.tourist;


import android.Manifest;
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
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class SubmitLuggageActivity extends AppCompatActivity {
    private long bookingID;
    Booking booking = null;
    EditText idET;
    AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
    private TextView detailsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitluggage);
        initializeUIComponents();
    }

    public void initializeUIComponents() {
        idET = findViewById(R.id.id);
        detailsTV = findViewById(R.id.details);
    }

    public void submit(View view) {
        String id = idET.getText().toString().trim();

        if (id.length() == 0) {
            idET.setError(Constants.MISSING_BOOKING_ID);
            idET.requestFocus();
        } else {
            bookingID = Long.parseLong(idET.getText().toString());
            booking = databaseHelper.getBookingWithID(bookingID);

            if (booking == null) {
                detailsTV.setVisibility(View.GONE);
                idET.setError(Constants.BOOKING_NOT_FOUND);
                idET.requestFocus();
            } else {
                if (booking.getOtp() != 0) {
                    Toast.makeText(getApplicationContext(), Constants.THIS_BOOKING_IS_AVAILED, Toast.LENGTH_LONG).show();
                } else {
                    int bookingYear = booking.getDate().get(Calendar.YEAR);
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

                    int bookingMonth = booking.getDate().get(Calendar.MONTH);
                    int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

                    int bookingDay = booking.getDate().get(Calendar.DATE);
                    int currentDay = Calendar.getInstance().get(Calendar.DATE);

                    if (bookingYear == currentYear) {
                        if (bookingMonth == currentMonth) {
                            if (bookingDay == currentDay) {
                                detailsTV.setVisibility(View.VISIBLE);
                                CloakRoom cloakRoom = databaseHelper.getCloakRoomWithID(booking.getCloakRoomID());
                                String description = "Per hour charges for the cloakroom: " + cloakRoom.getPerHourCharges() + Constants.RUPEE_SYMBOL;


                                detailsTV.setText(description + "\n OTP is sent to your registered mobile number. Please use this OTP when taking back the luggage");
                                int otp = generateOTP();
                                booking.setOtp(otp);
                                databaseHelper.updateOTPForBooking(booking);
                                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                        Manifest.permission.SEND_SMS)
                                        != PackageManager.PERMISSION_GRANTED) {

                                    // Permission is not granted
                                    // Should we show an explanation?
                                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                            Manifest.permission.SEND_SMS)) {

                                        // Show an explanation to the tourist *asynchronously* -- don't block
                                        // this thread waiting for the tourist's response! After the tourist
                                        // sees the explanation, try again to request the permission.

                                    } else {

                                        // No explanation needed; request the permission
                                        ActivityCompat.requestPermissions(this,
                                                new String[]{Manifest.permission.SEND_SMS},
                                                Constants.MY_PERMISSIONS_REQUEST_SEND_SMS);

                                        // MY_PERMISSIONS_REQUEST_SEND_SMS is an
                                        // app-defined int constant. The callback method gets the
                                        // result of the request.
                                    }
                                } else {
                                    // Permission has already been granted

                                    Tourist tourist = ((AppInstance)getApplicationContext()).getCurrentTourist();
                                    sendSms(tourist.getContact(),  "Thank you for using Android App. Your OTP for booking with ID : " + bookingID + "is: " + otp );

                                }

                            }
                            else if (bookingDay < currentDay )
                            {
                                detailsTV.setVisibility(View.VISIBLE);

                                detailsTV.setText("Booking date was in the past");
                            }
                            else
                            {
                                detailsTV.setVisibility(View.VISIBLE);

                                detailsTV.setText("This booking is for a future date");

                            }

                        }
                        else if (bookingMonth < currentMonth)
                        {
                            detailsTV.setVisibility(View.VISIBLE);
                            detailsTV.setText("Booking date was in the past");
                        }
                        else
                        {
                            detailsTV.setVisibility(View.VISIBLE);
                            detailsTV.setText("This booking is for a future date");
                        }

                    }
                    else if(bookingYear<currentYear)
                    {
                        detailsTV.setVisibility(View.VISIBLE);
                        detailsTV.setText("Booking date was in the past");
                    }
                    else
                    {
                        detailsTV.setVisibility(View.VISIBLE);
                        detailsTV.setText("This booking is for a future date");
                    }
                }
            }
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_SEND_SMS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Tourist tourist = ((AppInstance)getApplicationContext()).getCurrentTourist();

                    sendSms(tourist.getContact(),  "Thank you for using Android App. Your OTP for booking with ID : " + bookingID + "is: " + booking.getOtp() );

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
        }
    }

    public int generateOTP ()
    {
        int otp = 0;
        Random r = new Random();
        int lower_limit = 1000;
        int upper_limit = 10000;
        otp = r.nextInt(upper_limit-lower_limit)+lower_limit;
        return otp;
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

}
