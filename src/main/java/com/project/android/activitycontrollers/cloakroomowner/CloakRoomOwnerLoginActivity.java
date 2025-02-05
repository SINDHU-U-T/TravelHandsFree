package com.project.android.activitycontrollers.cloakroomowner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import com.project.android.R;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.CloakRoomOwner;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

public class CloakRoomOwnerLoginActivity extends AppCompatActivity {
    private EditText usernameET, passwordET;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloakroomlogin);
        initializeUIComponents();
    }

    public void initializeUIComponents() {
        usernameET = findViewById(R.id.userName);
        passwordET = findViewById(R.id.password);
    }

    public void login(View view)
    {
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        if (username.length() == 0)
        {
            usernameET.setError(Constants.MISSING_USERNAME);
            usernameET.requestFocus();
        } else if (password.length() == 0)
        {
            passwordET.setError(Constants.MISSING_PASSWORD);
            passwordET.requestFocus();
        }
        else
        {
            AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
            CloakRoomOwner cloakRoomOwner = databaseHelper.getCloakRoomOwnerWithUsernameAndPassword(username, password);
            if (cloakRoomOwner == null)
            {
                Toast.makeText(getApplicationContext(), Constants.INVALID_CREDENTIALS,Toast.LENGTH_SHORT).show();
            }
            else
            {
                ((AppInstance)getApplicationContext()).setCurrentCloakRoomOwner(cloakRoomOwner);
                Intent i = new Intent(this, CloakRoomOwnerHomeActivity.class);
                startActivity(i);
                finish();

            }
        }
    }

    public void forgotPassword(View view)
    {
        String userName = usernameET.getText().toString().trim();

        if (userName.length() == 0)
        {
            usernameET.setError(Constants.MISSING_USERNAME);
            usernameET.requestFocus();
        }
        else {
            AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
            CloakRoomOwner cloakRoomOwner = databaseHelper.getCloakRoomOwnerWithUserName(userName);
            if (cloakRoomOwner == null) {
                Toast.makeText(getApplicationContext(), Constants.UNREGISTERED_CLOAKROOMOWNER, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), Constants.FORGOT_PASSWORD, Toast.LENGTH_LONG).show();
                final String passwordString = "Hello " + cloakRoomOwner.getName() + ". \nYour registered password is: " + cloakRoomOwner.getPassword() + "\nTeam Android App";

                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED)
                {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.SEND_SMS))
                    {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    }
                    else
                    {

                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.SEND_SMS},
                                Constants.MY_PERMISSIONS_REQUEST_SEND_SMS);

                        // MY_PERMISSIONS_REQUEST_SEND_SMS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                }
                else
                {
                    sendSms(cloakRoomOwner.getContact(), passwordString);
                    // Permission has already been granted
                }


            }
        }
    }

    public void register(View view)
    {
        Intent i = new Intent(this, CloakRoomOwnerRegistrationActivity.class);
        startActivity(i);
        finish();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_SEND_SMS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String userName = usernameET.getText().toString().trim();
                    AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
                    CloakRoomOwner cloakRoomOwner = databaseHelper.getCloakRoomOwnerWithUserName(userName);

                    final String passwordString = "Hello " + cloakRoomOwner.getName() + ". \nYour registered password is: " + cloakRoomOwner.getPassword() + "\nTeam Android App";

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    sendSms(cloakRoomOwner.getContact(), passwordString);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
        }
    }

    private void sendSms(String phonenumber, String message)
    {
        SmsManager manager = SmsManager.getDefault();

        int length = message.length();

        if(length > 160)
        {
            ArrayList<String> messagelist = manager.divideMessage(message);

            manager.sendMultipartTextMessage(phonenumber, null, messagelist, null, null);
        }
        else
        {
            manager.sendTextMessage(phonenumber, null, message, null, null);
        }
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.about_menu, menu);
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
        }
        return false;
    }


}
