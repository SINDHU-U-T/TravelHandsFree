package com.project.android.activitycontrollers.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.CloakRoom;
import com.project.android.model.Feedback;
import com.project.android.model.Tourist;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

public class FeedbackDetailsActivity extends AppCompatActivity {
    private TextView touristTV, descriptionTV, mailTV, mobileTV,cloakRoomTV;
    private long feedbackID;
    private Feedback feedback = null;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackdetails);
        initializeUIComponents();
        feedbackID = getIntent().getLongExtra(Constants.ID_KEY, 0);
        displayData();
    }

    public void initializeUIComponents()
    {
        touristTV = findViewById(R.id.tourist);
        descriptionTV = findViewById(R.id.description);
        mailTV = findViewById(R.id.mail);
        mobileTV = findViewById(R.id.mobile);
        cloakRoomTV = findViewById(R.id.cloakRoom);
    }

    public void displayData()
    {
        AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
        feedback = databaseHelper.getFeedbackWithID(feedbackID);

        Tourist tourist = databaseHelper.getTouristWithID(feedback.getTouristID());
            descriptionTV.setText(feedback.getDescription());
        touristTV.setText(tourist.getName());
            mailTV.setText(tourist.getMail());
            mobileTV.setText(tourist.getContact());

            CloakRoom cloakRoom = databaseHelper.getCloakRoomWithID(feedback.getCloakRoomID());
            if (cloakRoom == null)
            {
                cloakRoomTV.setText(Constants.NOT_APPLICABLE);
            }
            else
            {
                cloakRoomTV.setText(cloakRoom.getName());
            }




    }

    public boolean onCreateOptionsMenu(android.view.Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.about:
                android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
                dialogBuilder.setIcon(R.drawable.logo);
                dialogBuilder.setTitle(R.string.app_name);
                dialogBuilder.setMessage(Constants.APP_DESCRIPTION);
                dialogBuilder.create();
                dialogBuilder.show();
                return true;

            case R.id.logout:
                ((AppInstance)getApplicationContext()).setAdminUser(false);
                Intent i = new Intent(this, UserSelectionActivity.class);
                startActivity(i);
                finish();
                return true;

            case R.id.changePassword:
                Intent intent = new Intent(this, AdminChangePasswordActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

}
