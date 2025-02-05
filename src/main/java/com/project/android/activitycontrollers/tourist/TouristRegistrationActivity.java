package com.project.android.activitycontrollers.tourist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.android.R;
import com.project.android.activitycontrollers.cloakroomowner.CloakRoomOwnerHomeActivity;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.CloakRoomOwner;
import com.project.android.model.Tourist;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;
import com.project.android.utility.ImageFilePath;
import com.project.android.utility.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TouristRegistrationActivity extends AppCompatActivity {
    AppDatabaseHelper databaseHelper;
    private EditText nameET, usernameET, passwordET, emailET, mobileET,confirmpasswordET;
    private ImageView iconIV;
    private String realPath =null;
    Tourist tourist =null;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touristregistration);
        databaseHelper = new AppDatabaseHelper(this);
        initializeUIComponents();
    }
    public void initializeUIComponents()
    {

        usernameET =  findViewById(R.id.userName);
        passwordET = findViewById(R.id.password);
        emailET = findViewById(R.id.mail);
        mobileET = findViewById(R.id.contact);
        nameET =findViewById(R.id.name);
        confirmpasswordET=findViewById(R.id.confirmpassword);
        iconIV=findViewById(R.id.image);

    }
    public void register(View view)
    {
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String name =nameET.getText().toString().trim();

        String confirmpassword =confirmpasswordET.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String mobile = mobileET.getText().toString().trim();

        ArrayList<String> userNames = databaseHelper.getAllTouristUserNames();

        if(name.length()==0)
        {
            nameET.setError(Constants.MISSING_NAME);
            nameET.requestFocus();
        }
        else if (TextUtils.isEmpty(email))

        {
            emailET.setError(Constants.MISSING_EMAIL);
            emailET.requestFocus();
        }
        else if(false== Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailET.setError(Constants.INVALID_EMAIL);
            emailET.requestFocus();
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
        else if (username.length() == 0)
        {
            usernameET.setError(Constants.MISSING_USERNAME);
            usernameET.requestFocus();
        }
        else if (userNames.size()>0 && userNames.contains(username)){
            usernameET.setError(Constants.DUPLICATE_USERNAME);
            usernameET.requestFocus();
        }
        else if (password.length() == 0)
        {
            passwordET.setError(Constants.MISSING_PASSWORD);
            passwordET.requestFocus();
        }
        else if(password.length()<Constants.MINIMUM_PASSWORD_LENGTH)
        {
            passwordET.setError(Constants.INVALID_PASSWORD);
            passwordET.requestFocus();
        }
        else if (confirmpassword.length() == 0) {
            confirmpasswordET.setError(Constants.MISSING_PASSWORD_CONFIRMATION);
            confirmpasswordET.requestFocus();
        }
        else if (!password.equals(confirmpassword)) {
            confirmpasswordET.setError(Constants.PASSWORD_MISMATCH);
            confirmpasswordET.requestFocus();
        }
        else if (false == isValidPassword(password))
        {
            passwordET.setError(Constants.INVALID_PASSWORD);
            passwordET.requestFocus();
        }
        else if(realPath ==null)
        {
            Toast.makeText(getApplicationContext(),Constants.MISSING_TOURIST_PROFILEPHOTO,Toast.LENGTH_SHORT).show();
        }
        else
        {
            tourist = new Tourist();
            tourist.setName(name);
            tourist.setMail(email);
            tourist.setContact(mobile);
            tourist.setPassword(password);
            tourist.setUsername(username);
            tourist.setProfilePath(realPath);
            AppDatabaseHelper databaseHelper=new AppDatabaseHelper(this);
            long touristID= databaseHelper.addTourist(tourist);
            tourist.setTouristID(touristID);
            ((AppInstance)getApplicationContext()).setCurrentTourist(tourist);

            Intent intent=new Intent(this,TouristHomeActivity.class);
            startActivity(intent);
            finish();

            Toast.makeText(getApplicationContext(), Constants.TOURIST_REGISTERED_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isValidPassword(final String password)
    {
        Pattern pattern;

        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[*@#$%^&+=!])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);

        matcher = pattern.matcher(password);

        return matcher.matches();

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

                boolean result= Utility.checkPermission(TouristRegistrationActivity.this);
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
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
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
