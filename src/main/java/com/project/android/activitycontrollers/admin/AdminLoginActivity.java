package com.project.android.activitycontrollers.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.android.R;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

public class AdminLoginActivity extends AppCompatActivity {
    private EditText usernameET, passwordET;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        initializeUIComponents();
    }

    public void initializeUIComponents()
    {
        usernameET = findViewById(R.id.userName);
        passwordET = findViewById(R.id.password);
    }

    public void login(View view)
    {
        String userName = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(this);
        String adminPassword = Constants.ADMIN_PASSWORD;
        if (mypref.contains(Constants.ADMIN_PASSWORD_KEY))
        {
            adminPassword = mypref.getString(Constants.ADMIN_PASSWORD_KEY, Constants.ADMIN_PASSWORD);
        }

        if (userName.length() == 0)
        {
            usernameET.setError(Constants.MISSING_USERNAME);
            usernameET.requestFocus();
        }
        else if (password.length() == 0)
        {
            passwordET.setError(Constants.MISSING_PASSWORD);
            passwordET.requestFocus();
        }
        // Validate credentials
        else if (userName.equals("admin") && password.equals(adminPassword))
        {
            ((AppInstance)getApplicationContext()).setAdminUser(true);
            Intent i = new Intent(this, AdminHomeActivity.class);
            startActivity(i);
            // close this activity
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), Constants.INVALID_ADMIN_CREDENTIALS, Toast.LENGTH_SHORT).show();

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
