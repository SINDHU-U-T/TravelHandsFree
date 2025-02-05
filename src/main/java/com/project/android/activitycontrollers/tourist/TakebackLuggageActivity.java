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

public class TakebackLuggageActivity extends AppCompatActivity {
    private long bookingID;
    Booking booking = null;
    EditText idET, otpET;
    AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
    private TextView detailsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takebackluggage);
        initializeUIComponents();
    }

    public void initializeUIComponents() {
        idET = findViewById(R.id.id);
        otpET = findViewById(R.id.otp);
        detailsTV = findViewById(R.id.details);
    }

    public void submit(View view) {
        String id = idET.getText().toString().trim();
        String otp = otpET.getText().toString().trim();

        if (id.length() == 0) {
            idET.setError(Constants.MISSING_BOOKING_ID);
            idET.requestFocus();
        }
        else  if (otp.length() == 0) {
            otpET.setError(Constants.MISSING_OTP);
            otpET.requestFocus();
        }
        else {
            bookingID = Long.parseLong(idET.getText().toString());
            booking = databaseHelper.getBookingWithID(bookingID);

            if (booking == null) {
                detailsTV.setVisibility(View.GONE);
                idET.setError(Constants.BOOKING_NOT_FOUND);
                idET.requestFocus();
            } else {
                if (booking.getOtp() != Integer.parseInt(otp)) {
                    Toast.makeText(getApplicationContext(), Constants.THIS_OTP_IS_INVALID, Toast.LENGTH_LONG).show();
                } else {
                    detailsTV.setVisibility(View.VISIBLE);
                    detailsTV.setText("Thank you for using our Cloakroom. Please collect your luggage");
                }
            }
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



