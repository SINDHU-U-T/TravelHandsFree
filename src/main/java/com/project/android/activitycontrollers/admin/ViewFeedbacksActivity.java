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

import java.util.ArrayList;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.adapters.FeedbackListItemAdapter;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Feedback;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

public class ViewFeedbacksActivity extends AppCompatActivity {
    private ListView listView = null;

    FeedbackListItemAdapter customAdapter;
    ArrayList<Feedback> feedbackList;
    private TextView mNoFeedbackView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfeedbacks);
        populateListView();
    }

    public void populateListView()
    {
        mNoFeedbackView = findViewById(R.id.no_feedback_text);
        mNoFeedbackView.setText(Constants.NOFEEDBACK_DESCRIPTION );

        final AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
        feedbackList = databaseHelper.getFeedbackList();

        if (feedbackList.size() > 0)
        {
            mNoFeedbackView.setVisibility(View.GONE);
            customAdapter = new FeedbackListItemAdapter(this, R.layout.allfeedbacklist_item);
            customAdapter.setFeedbackList(feedbackList);
            listView = findViewById(R.id.listView);
            listView.setAdapter(customAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Feedback feedback = feedbackList.get(i);
                    Intent intent = new Intent(getApplicationContext(), FeedbackDetailsActivity.class);

                    intent.putExtra(Constants.ID_KEY, feedback.getFeedbackID());
                    startActivity(intent);
                }
            });

        } else
        {
            mNoFeedbackView.setVisibility(View.VISIBLE);
            if (customAdapter != null)
            {
                reloadData();
            }
        }
    }

    public void reloadData()
    {
        feedbackList.clear();
        customAdapter.setFeedbackList(feedbackList);
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
