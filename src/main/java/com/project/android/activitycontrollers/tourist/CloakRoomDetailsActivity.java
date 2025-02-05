package com.project.android.activitycontrollers.tourist;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.DatePicker;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CloakRoomDetailsActivity extends AppCompatActivity {
    private TextView nameTV, addressTV, mobileTV, openingTV, closingTV, perHourChargesTV;
    private long cloakRoomID;
    private CloakRoom cloakRoom = null;
    private ImageView iconIV;
    private TextView dateTV;

    private Calendar date;

    private Booking booking = null;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookcloakroomdetails);
        initializeUIComponents();
        cloakRoomID = getIntent().getLongExtra(Constants.ID_KEY, 0);
        displayData();
    }

    public void initializeUIComponents()
    {
        nameTV = findViewById(R.id.name);
        addressTV = findViewById(R.id.address);
        mobileTV = findViewById(R.id.mobile);
        openingTV = findViewById(R.id.openingtime);
        closingTV = findViewById(R.id.closingtime);
        perHourChargesTV = findViewById(R.id.perHourCharge);
        iconIV = findViewById(R.id.profile);
        dateTV = findViewById(R.id.date);
    }

    public void displayData()
    {
        AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
        cloakRoom = databaseHelper.getCloakRoomWithID(cloakRoomID);

        nameTV.setText(cloakRoom.getName());
        addressTV.setText(cloakRoom.getAddress());
        mobileTV.setText(cloakRoom.getContact());
        openingTV.setText(cloakRoom.getOpensAt());
        closingTV.setText(cloakRoom.getClosesAt());
        perHourChargesTV.setText(Constants.RUPEE_SYMBOL + cloakRoom.getPerHourCharges());
        String imagePath = cloakRoom.getProfilePath();
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        if (null != bitmap) {
            iconIV.setImageBitmap(bitmap);
        }
        else
        {
            int resID = this.getResources().getIdentifier("noimage", Constants.DRAWABLE_RESOURCE, this.getPackageName());
            iconIV.setImageResource(resID);
        }
    }




    public void selectDate(View view)
    {
        dateTV.setError(null);
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                String expenseDate = dateFormatter.format(newDate.getTime());
                dateTV.setText(expenseDate);
                date = newDate;
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

    public void bookCloakRoom(View view)
    {
        if(date == null) {
            dateTV.requestFocus();
            dateTV.setError(Constants.MISSING_DATE);
        }
        else
        {
            Tourist tourist = ((AppInstance)getApplicationContext()).getCurrentTourist();

            booking = new Booking();
            booking.setDate(date);

            booking.setTouristID(tourist.getTouristID());
            booking.setCloakRoomID(cloakRoomID);

            AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
            long bookingID = databaseHelper.addBooking(booking);
            booking.setBookingID(bookingID);
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(Constants.BOOKING_PROGRESS); // Setting Message
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();
                    loadHomeActivity();
                }
            }).start();

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

                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String date = dateFormatter.format(booking.getDate().getTime());

                sendSms(tourist.getContact(),  "Thank you for using Android App app. Cloak Room with the below details is booked successfully.\n" + "Booking ID: " + bookingID + "\nCloak Room: " + cloakRoom.getName() + "\nAddress: " + cloakRoom.getAddress() + "\nContact: " + cloakRoom.getContact() + "\nDate: " + date );
                sendSms(cloakRoom.getContact(),  "Cloak Room with the below details is booked using Android App app.\n" + "Booking ID: " + bookingID +  "\nTourist: " + tourist.getName() + "\nDate: " + date );

            }
        }
    }
    public void loadHomeActivity()
    {

        Intent i = new Intent(this, TouristHomeActivity.class);
        startActivity(i);
        finish();
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

                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    String date = dateFormatter.format(booking.getDate().getTime());
                    Tourist tourist = ((AppInstance)getApplicationContext()).getCurrentTourist();

                    sendSms(tourist.getContact(),  "Thank you for using Android App app. Cloak Room with the below details is booked successfully.\n" + "Booking ID: " + booking.getBookingID() + "\nCloak Room: " + cloakRoom.getName() + "\nAddress: " + cloakRoom.getAddress() + "\nContact: " + cloakRoom.getContact() + "\nDate: " + date );
                    sendSms(cloakRoom.getContact(),  "Cloak Room with the below details is booked using Android App app.\n" + "Booking ID: " + booking.getBookingID() +  "\nTourist: " + tourist.getName() + "\nDate: " + date );

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
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

}
