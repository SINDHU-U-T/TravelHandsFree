package com.project.android.activitycontrollers.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.adapters.TouristListItemAdapter;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Tourist;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

import java.util.ArrayList;

public class ViewTouristsActivity extends AppCompatActivity {
    private ListView listView = null;

    TouristListItemAdapter customAdapter;
    ArrayList<Tourist> touristList;
    private TextView mNoTouristView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtourists);
        populateListView();
    }

    public void populateListView()
    {
        mNoTouristView = findViewById(R.id.no_tourist_text);
        mNoTouristView.setText(Constants.NOTOURIST_DESCRIPTION );

        final AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
        touristList = databaseHelper.getTouristList();

        if (touristList.size() > 0)
        {
            mNoTouristView.setVisibility(View.GONE);
            customAdapter = new TouristListItemAdapter(this, R.layout.allfeedbacklist_item);
            customAdapter.setTouristList(touristList);
            listView = findViewById(R.id.listView);
            listView.setAdapter(customAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Tourist tourist = touristList.get(i);
                    Intent intent = new Intent(getApplicationContext(), TouristDetailsActivity.class);

                    intent.putExtra(Constants.ID_KEY, tourist.getTouristID());
                    startActivity(intent);
                }
            });

        } else
        {
            mNoTouristView.setVisibility(View.VISIBLE);
            if (customAdapter != null)
            {
                reloadData();
            }
        }
    }

    public void reloadData()
    {
        touristList.clear();
        customAdapter.setTouristList(touristList);
        customAdapter.notifyDataSetChanged();
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
