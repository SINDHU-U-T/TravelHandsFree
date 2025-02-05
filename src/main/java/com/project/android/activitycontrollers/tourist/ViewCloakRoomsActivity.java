package com.project.android.activitycontrollers.tourist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.adapters.CloakRoomListItemAdapter;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.CloakRoom;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

import java.util.ArrayList;

public class ViewCloakRoomsActivity extends AppCompatActivity {
    private ListView listView = null;

    CloakRoomListItemAdapter customAdapter;
    ArrayList<CloakRoom> cloakRoomList;
    private TextView mNoCloakRoomsView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcloakrooms);
        populateListView();
    }

    public void populateListView()
    {
        mNoCloakRoomsView = findViewById(R.id.no_cloakroom_text);
        mNoCloakRoomsView.setText(Constants.NOCLOAKROOM_DESCRIPTION );

        final AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
        cloakRoomList = databaseHelper.getCloakRoomList();

        if (cloakRoomList.size() > 0)
        {
            mNoCloakRoomsView.setVisibility(View.GONE);
            customAdapter = new CloakRoomListItemAdapter(this, R.layout.allfeedbacklist_item);
            customAdapter.setCloakRoomList(cloakRoomList);
            listView = findViewById(R.id.listView);
            listView.setAdapter(customAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    CloakRoom cloakRoom = cloakRoomList.get(i);
                    Intent intent = new Intent(getApplicationContext(), CloakRoomDetailsActivity.class);

                    intent.putExtra(Constants.ID_KEY, cloakRoom.getCloakRoomID());
                    startActivity(intent);
                }
            });

        } else
        {
            mNoCloakRoomsView.setVisibility(View.VISIBLE);
            if (customAdapter != null)
            {
                reloadData();
            }
        }
    }

    public void reloadData()
    {
        cloakRoomList.clear();
        customAdapter.setCloakRoomList(cloakRoomList);
        customAdapter.notifyDataSetChanged();
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

}
