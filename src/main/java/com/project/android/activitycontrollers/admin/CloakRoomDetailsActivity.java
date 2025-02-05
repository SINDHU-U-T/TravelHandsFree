package com.project.android.activitycontrollers.admin;

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
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.CloakRoom;
import com.project.android.model.CloakRoomOwner;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;


public class CloakRoomDetailsActivity extends AppCompatActivity {
    private TextView nameTV, addressTV, mobileTV, openingTV, closingTV, perHourChargesTV, ownerTV, contactTV, mailTV;
    private long cloakRoomID;
    private CloakRoom cloakRoom = null;
    private ImageView iconIV;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloakroomdetails);
        initializeUIComponents();
        cloakRoomID = getIntent().getLongExtra(Constants.ID_KEY, 0);
        displayData();
    }

    public void initializeUIComponents()
    {
        nameTV = findViewById(R.id.name);
        addressTV = findViewById(R.id.address);
        mobileTV = findViewById(R.id.mobile);
        openingTV = findViewById(R.id.openingtime);
        closingTV = findViewById(R.id.closingtime);
        perHourChargesTV = findViewById(R.id.perHourCharge);
        ownerTV = findViewById(R.id.owner);
        contactTV = findViewById(R.id.contact);
        mailTV = findViewById(R.id.mail);
        iconIV = findViewById(R.id.profile);
    }

    public void displayData()
    {
        AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
        cloakRoom = databaseHelper.getCloakRoomWithID(cloakRoomID);
        CloakRoomOwner owner = databaseHelper.getCloakRoomOwnerWithID(cloakRoom.getOwnerID());
        nameTV.setText(cloakRoom.getName());
        addressTV.setText(cloakRoom.getAddress());
        mobileTV.setText(cloakRoom.getContact());
        openingTV.setText(cloakRoom.getOpensAt());
        closingTV.setText(cloakRoom.getClosesAt());
        perHourChargesTV.setText(Constants.RUPEE_SYMBOL + cloakRoom.getPerHourCharges());
        ownerTV.setText(owner.getName());
        contactTV.setText(owner.getContact());
        mailTV.setText(owner.getMail());
        String imagePath = cloakRoom.getProfilePath();
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
