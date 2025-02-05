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
import com.project.android.adapters.BookingsListItemAdapter;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Booking;
import com.project.android.model.Tourist;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

import java.util.ArrayList;

public class ViewBookingsActivity extends AppCompatActivity {
    private ListView listView = null;

    BookingsListItemAdapter customAdapter;
    ArrayList<Booking> bookings;
    private TextView mNoBookingsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbookings);
        populateListView();

    }

    public void populateListView() {

        Tourist tourist = ((AppInstance)getApplicationContext()).getCurrentTourist();

        mNoBookingsView = findViewById(R.id.no_bookings_text);
        mNoBookingsView.setText(Constants.NOBOOKINGS_FOR_TOURIST_DESCRIPTION + tourist.getName() );

        final AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);


        bookings = databaseHelper.getAllBookingsForTouristWithID(tourist.getTouristID());
        if (bookings.size() > 0) {
            mNoBookingsView.setVisibility(View.GONE);
            customAdapter = new BookingsListItemAdapter(this, R.layout.allbookingslist_item);
            customAdapter.setBookings(bookings);
            listView = findViewById(R.id.listView);
            listView.setAdapter(customAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Booking booking = bookings.get(i);
                    Intent intent = new Intent(getApplicationContext(), BookingDetailsActivity.class);
                    intent.putExtra(Constants.ID_KEY, booking.getBookingID());
                    startActivity(intent);
                    finish();
                }
            });

        } else {
            mNoBookingsView.setVisibility(View.VISIBLE);
            if (customAdapter != null) {
                reloadData();
            }
        }
    }

    public void reloadData() {
        bookings.clear();
        customAdapter.setBookings(bookings);
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
