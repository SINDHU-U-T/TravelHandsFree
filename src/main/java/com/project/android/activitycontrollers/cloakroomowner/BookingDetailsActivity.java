package com.project.android.activitycontrollers.cloakroomowner;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.activitycontrollers.tourist.ChangePasswordActivity;
import com.project.android.activitycontrollers.tourist.TouristHomeActivity;
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
    private Button cancelBookingBT;
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
        Tourist tourist = databaseHelper.getTouristWithID(booking.getTouristID());

        touristTV.setText(tourist.getName());

    }

    public void initializeUIComponents() {
        cloakRoomTV = findViewById(R.id.cloakRoom);
        dateTV = findViewById(R.id.date);
        touristTV =  findViewById(R.id.tourist);
        cancelBookingBT = findViewById(R.id.cancelBooking);
        cancelBookingBT.setVisibility(View.GONE);
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
                ((AppInstance) getApplicationContext()).setCurrentCloakRoomOwner(null);
                Intent i = new Intent(this, UserSelectionActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.about:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
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
                AlertDialog.Builder helpDialogBuilder = new AlertDialog.Builder(this);
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
