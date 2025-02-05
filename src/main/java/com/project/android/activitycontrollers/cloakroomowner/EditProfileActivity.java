package com.project.android.activitycontrollers.cloakroomowner;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.activitycontrollers.tourist.ChangePasswordActivity;
import com.project.android.database.AppDatabaseHelper;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class EditProfileActivity extends AppCompatActivity {
    private EditText passwordET, mailET, mobileET;
    private String realPath = null;
    private ImageView iconIV;
    CloakRoomOwner owner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloakroomownereditprofile);
        initializeUIComponents();
    }

    public void initializeUIComponents() {
        mailET = findViewById(R.id.mail);
        mobileET = findViewById(R.id.contact);
        passwordET = findViewById(R.id.password);
        owner = ((AppInstance) getApplicationContext()).getCurrentCloakRoomOwner();
        passwordET.setText(owner.getPassword());
        mailET.setText(owner.getMail());
        mobileET.setText(owner.getContact());
        iconIV = findViewById(R.id.icon);
        iconIV.setVisibility(View.VISIBLE);
        realPath = owner.getProfilePath();
        Bitmap bitmap = BitmapFactory.decodeFile(realPath);
        if (null != bitmap) {
            iconIV.setImageBitmap(bitmap);
        }
        else
        {
            int resID = this.getResources().getIdentifier("noimage", Constants.DRAWABLE_RESOURCE, this.getPackageName());
            iconIV.setImageResource(resID);
        }

    }

    public void editDetails(View view) {
        String password = passwordET.getText().toString().trim();
        String mail = mailET.getText().toString().trim();
        String mobile = mobileET.getText().toString().trim();


        AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);

        if (password.length() == 0) {
            passwordET.setError(Constants.MISSING_PASSWORD);
            passwordET.requestFocus();
        } else if (password.length() < Constants.MINIMUM_PASSWORD_LENGTH) {
            passwordET.setError(Constants.INVALID_PASSWORD);
            passwordET.requestFocus();
        } else if (false == isValidPassword(password)) {
            passwordET.setError(Constants.INVALID_PASSWORD);
            passwordET.requestFocus();
        } else if (TextUtils.isEmpty(mail)) {
            mailET.setError(Constants.MISSING_EMAIL);
            mailET.requestFocus();
        } else if (false == android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            mailET.setError(Constants.INVALID_EMAIL);
            mailET.requestFocus();
        } else if (mobile.length() == 0) {
            mobileET.setError(Constants.MISSING_MOBILE);
            mobileET.requestFocus();
        } else if (mobile.length() != 10) {
            mobileET.setError(Constants.INVALID_MOBILE);
            mobileET.requestFocus();

        }else if (realPath == null)
        {
            Toast.makeText(getApplicationContext(), Constants.MISSING_PROFILEPHOTO, Toast.LENGTH_SHORT).show();
        }
        else {
            owner.setMail(mail);
            owner.setContact(mobile);
            owner.setPassword(password);
            owner.setProfilePath(realPath);

            databaseHelper.updateDetailsForOwner(owner);
            Toast.makeText(this, Constants.DETAILS_EDITED_SUCCESSFULLY, Toast.LENGTH_SHORT).show();

            // close this activity
            finish();
        }
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

                boolean result= Utility.checkPermission(EditProfileActivity.this);
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


    public boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[*@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
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
