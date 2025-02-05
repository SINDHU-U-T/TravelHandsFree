package com.project.android.activitycontrollers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import com.project.android.R;

import com.project.android.activitycontrollers.admin.AdminLoginActivity;
import com.project.android.activitycontrollers.cloakroomowner.CloakRoomOwnerLoginActivity;
import com.project.android.activitycontrollers.tourist.TouristLoginActivity;
import com.project.android.utility.Constants;

public class UserSelectionActivity extends AppCompatActivity {
    private RadioGroup userTypeRG;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userselectionscreen);
        initializeUIComponents();
    }

    private void initializeUIComponents() {
        userTypeRG = findViewById(R.id.userType);
        userTypeRG.check(R.id.admin);
    }
    public void selectUser(View view)
    {
        int selectedOption = userTypeRG.getCheckedRadioButtonId();
        Intent i = null;
        if (selectedOption == R.id.admin)
        {
            i = new Intent(getApplicationContext(),AdminLoginActivity.class);
        }
        else if(selectedOption==R.id.cloakRoom)
        {
            i = new Intent(getApplicationContext(),CloakRoomOwnerLoginActivity.class);
        }
        else
        {
            i=new Intent(getApplicationContext(),TouristLoginActivity.class);
        }
        startActivity(i);
        finish();
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
