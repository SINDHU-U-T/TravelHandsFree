package com.project.android.activitycontrollers.tourist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;
import com.project.android.model.Tourist;

public class TouristViewProfileActivity extends AppCompatActivity {
    private TextView nameTV, mobileTV, mailTV;
    Tourist tourist = null;
    private ImageView profileIV;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtouristprofile);
        initializeUIComponents();

        displayData();
    }

    public void initializeUIComponents()
    {
        nameTV = findViewById(R.id.name);
        mobileTV = findViewById(R.id.contact);
        mailTV = findViewById(R.id.mail);
        profileIV = findViewById(R.id.profile);
    }

    public void displayData()
    {
        tourist = ((AppInstance)getApplicationContext()).getCurrentTourist();
        if (tourist != null)
        {
            nameTV.setText(tourist.getName());
            mobileTV.setText(tourist.getContact());
            mailTV.setText(tourist.getMail());
            String imagePath = tourist.getProfilePath();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (null != bitmap) {
                profileIV.setImageBitmap(bitmap);
            }
            else
            {
                int resID = this.getResources().getIdentifier("noimage", Constants.DRAWABLE_RESOURCE, this.getPackageName());
                profileIV.setImageResource(resID);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sub_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.help:
                android.app.AlertDialog.Builder helpDialogBuilder = new android.app.AlertDialog.Builder(this);
                helpDialogBuilder.setIcon(R.drawable.logo);
                helpDialogBuilder.setTitle(R.string.app_name);
                helpDialogBuilder.setMessage(Constants.HELP_MESSAGE);
                helpDialogBuilder.create();
                helpDialogBuilder.show();
                return true;
            case R.id.changePassword:
                Intent intent = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent);
                return true;

            case R.id.feedback:

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, Constants.FEEDBACK_SUBJECT);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{Constants.FEEDBACK_MAILID});

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send feedback..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_LONG).show();
                }


                return true;

            case R.id.about:
                android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
                dialogBuilder.setIcon(R.drawable.logo);
                dialogBuilder.setTitle(R.string.app_name);
                dialogBuilder.setMessage(Constants.APP_DESCRIPTION);
                dialogBuilder.create();
                dialogBuilder.show();
                return true;


            case R.id.logout:
                ((AppInstance)getApplicationContext()).setCurrentTourist(null);
                Intent i = new Intent(this, UserSelectionActivity.class);
                startActivity(i);
                finish();
                return true;

        }
        return false;
    }


}
