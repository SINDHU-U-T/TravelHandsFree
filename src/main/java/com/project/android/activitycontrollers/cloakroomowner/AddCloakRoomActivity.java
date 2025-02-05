package com.project.android.activitycontrollers.cloakroomowner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.CloakRoom;
import com.project.android.model.CloakRoomOwner;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;
import com.project.android.utility.ImageFilePath;
import com.project.android.utility.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AddCloakRoomActivity extends AppCompatActivity {
    AppDatabaseHelper databaseHelper;
    private EditText nameET, addressET, mobileET, perHourChargesET;
    private ImageView iconIV;
    private String realPath =null;
    CloakRoom cloakRoom =null;
    private TextView openingTimeTV;
    private TextView closingTimeTV;
    private String openingTime = null;
    private String closingTime = null;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcloakroom);
        databaseHelper = new AppDatabaseHelper(this);
        initializeUIComponents();
    }
    public void initializeUIComponents()
    {

        addressET =  findViewById(R.id.address);
        mobileET = findViewById(R.id.contact);
        nameET =findViewById(R.id.name);
        iconIV=findViewById(R.id.image);
        perHourChargesET = findViewById(R.id.charges);
        openingTimeTV = findViewById(R.id.openingtime);
        closingTimeTV = findViewById(R.id.closingtime);


    }
    public void addCloakRoom(View view)
    {
        String name =nameET.getText().toString().trim();

        String mobile = mobileET.getText().toString().trim();
        String address = addressET.getText().toString().trim();
        String perHourCharges = perHourChargesET.getText().toString().trim();


        if(name.length()==0)
        {
            nameET.setError(Constants.MISSING_NAME);
            nameET.requestFocus();
        }
        else if (mobile.length() == 0)
        {
            mobileET.setError(Constants.MISSING_MOBILE);
            mobileET.requestFocus();
        }
        else if (mobile.length() != 10)
        {
            mobileET.setError(Constants.INVALID_MOBILE);
            mobileET.requestFocus();
        }
        else if (address.length() == 0)
        {
            addressET.setError(Constants.MISSING_ADDRESS);
            addressET.requestFocus();
        }

        else if (perHourCharges.length() == 0)
        {
            perHourChargesET.setError(Constants.MISSING_PER_HOUR_CHARGES);
            perHourChargesET.requestFocus();
        }
        else if(realPath ==null)
        {
            Toast.makeText(getApplicationContext(),Constants.MISSING_CLOAK_ROOM_PHOTO,Toast.LENGTH_SHORT).show();
        }
        else
        {
            cloakRoom = new CloakRoom();
            cloakRoom.setName(name);
            cloakRoom.setAddress(address);
            cloakRoom.setContact(mobile);
            cloakRoom.setProfilePath(realPath);
            cloakRoom.setPerHourCharges(perHourCharges);
            cloakRoom.setOpensAt(openingTime);
            cloakRoom.setClosesAt(closingTime);

            CloakRoomOwner cloakRoomOwner = ((AppInstance)getApplicationContext()).getCurrentCloakRoomOwner();

            cloakRoom.setOwnerID(cloakRoomOwner.getOwnerID());
            AppDatabaseHelper databaseHelper=new AppDatabaseHelper(this);
            long cloakRoomID= databaseHelper.addCloakRoom(cloakRoom);
            cloakRoom.setCloakRoomID(cloakRoomID);
            Toast.makeText(getApplicationContext(), Constants.CLOAKROOM_ADDED_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void selectOpeningTime(View view) {
        openingTimeTV.setError(null);
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String minute = null;

                if (selectedMinute >= 10)
                    minute =  String.valueOf(selectedMinute);
                else
                    minute =
                            "0" + String.valueOf(selectedMinute);
                openingTime = (selectedHour > 12) ?
                        ((selectedHour % 12) + ":" + minute + " PM") :
                        (selectedHour) + ":" + minute + " AM";

                openingTimeTV.setText(openingTime);
            }
        }, hour, minute, true);//Yes 24 hour time

        mTimePicker.setTitle("Select Opening Time");
        mTimePicker.show();
    }

    public void selectClosingTime(View view) {
        closingTimeTV.setError(null);
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String minute = null;

                if (selectedMinute >= 10)
                    minute =  String.valueOf(selectedMinute);
                else
                    minute =
                            "0" + String.valueOf(selectedMinute);
                closingTime = (selectedHour > 12) ?
                        ((selectedHour % 12) + ":" + minute + " PM") :
                        (selectedHour) + ":" + minute + " AM";

                closingTimeTV.setText(closingTime);
            }
        }, hour, minute, true);//Yes 24 hour time

        mTimePicker.setTitle("Select Opening Time");
        mTimePicker.show();
    }

    public void selectPhoto(View view)
    {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                boolean result= Utility.checkPermission(AddCloakRoomActivity.this);
                if (items[item].equals("Take Photo")) {
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constants.REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),Constants.SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == Constants.REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Uri uri = data.getData();


        realPath = ImageFilePath.getPath(this, data.getData());

        try
        {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

            iconIV.setImageBitmap(bitmap);
            iconIV.setVisibility(View.VISIBLE);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            Uri fileUri = Uri.fromFile(destination);
            realPath = ImageFilePath.getPath(this, fileUri);
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        iconIV.setImageBitmap(thumbnail);
        iconIV.setVisibility(View.VISIBLE);
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

}
