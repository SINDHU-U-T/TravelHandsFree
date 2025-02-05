package com.project.android.activitycontrollers.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

import java.util.ArrayList;

public class AdminHomeActivity  extends AppCompatActivity {
    private RadioGroup optionsRG;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);
        initializeUIComponents();
    }

    public void initializeUIComponents()
    {
        optionsRG = findViewById(R.id.options);
        optionsRG.check(R.id.viewTourists);
    }

    public void next(View view)
    {
        int selectedOption = optionsRG.getCheckedRadioButtonId();
        Intent i = null;

        if(selectedOption==R.id.viewTourists)
        {
            i = new Intent(getApplicationContext(),ViewTouristsActivity.class);
        }
        else if(selectedOption==R.id.viewCloakRooms)
        {
            i = new Intent(getApplicationContext(),ViewCloakRoomsActivity.class);
        }
        else
        {
            i=new Intent(getApplicationContext(),ViewFeedbacksActivity.class);
        }
        startActivity(i);

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

    public void onBackPressed()
    {
        moveTaskToBack(true);
    }

}
