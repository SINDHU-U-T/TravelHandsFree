package com.project.android.activitycontrollers.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Tourist;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

public class TouristDetailsActivity extends AppCompatActivity {
    private TextView touristTV, mailTV, mobileTV;
    private long touristID;
    private Tourist tourist = null;
    private ImageView iconIV;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touristdetails);
        initializeUIComponents();
        touristID = getIntent().getLongExtra(Constants.ID_KEY, 0);
        displayData();
    }

    public void initializeUIComponents()
    {
        touristTV = findViewById(R.id.tourist);
        mailTV = findViewById(R.id.mail);
        mobileTV = findViewById(R.id.mobile);
        iconIV = findViewById(R.id.profile);

    }

    public void displayData()
    {
        AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
        tourist = databaseHelper.getTouristWithID(touristID);

        Tourist tourist = databaseHelper.getTouristWithID(this.tourist.getTouristID());
        touristTV.setText(tourist.getName());
        mailTV.setText(tourist.getMail());
        mobileTV.setText(tourist.getContact());
        String imagePath = tourist.getProfilePath();
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
