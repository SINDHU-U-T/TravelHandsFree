package com.project.android.activitycontrollers.tourist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.activitycontrollers.admin.ViewFeedbacksActivity;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

public class TouristHomeActivity extends AppCompatActivity {
    private RadioGroup optionsRG;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touristhome);
        initializeUIComponents();
    }

    public void initializeUIComponents()
    {
        optionsRG = findViewById(R.id.options);
        optionsRG.check(R.id.cloakRoom);
    }

    public void next(View view)
    {
        int selectedOption = optionsRG.getCheckedRadioButtonId();
        Intent i = null;
        if(selectedOption==R.id.cloakRoom)
        {
            i = new Intent(getApplicationContext(),CloakRoomActivity.class);
        }
        else if(selectedOption==R.id.editProfile)
        {
            i = new Intent(getApplicationContext(),TouristEditProfileActivity.class);
        }
        else if(selectedOption==R.id.viewProfile)
        {
            i = new Intent(getApplicationContext(),TouristViewProfileActivity.class);
        }

        startActivity(i);

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

    public void onBackPressed()
    {
        moveTaskToBack(true);
    }

}
