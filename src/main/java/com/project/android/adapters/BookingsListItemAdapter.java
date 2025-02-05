package com.project.android.adapters;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.android.R;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Booking;
import com.project.android.model.CloakRoom;
import com.project.android.model.Tourist;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BookingsListItemAdapter extends ArrayAdapter<Booking>
{
    private Activity context;
    List<Booking> bookings;
    LayoutInflater inflater;

    public BookingsListItemAdapter(Activity context, int resourceId)
    {
        super(context, resourceId);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder
    {
        TextView cloakRoomTV;
        TextView touristTV;
        TextView dateTV;
        ImageView imageIV;
    }

    public View getView(int position, View view, ViewGroup parent) {

        final BookingsListItemAdapter.ViewHolder holder;
        if (view == null) {
            holder = new BookingsListItemAdapter.ViewHolder();
            view = inflater.inflate(R.layout.allbookingslist_item, null);
            holder.cloakRoomTV = view.findViewById(R.id.cloakroom);
            holder.touristTV = view.findViewById(R.id.tourist);

            holder.dateTV =  view.findViewById(R.id.date);
            holder.imageIV =view.findViewById(R.id.image);
            view.setTag(holder);
        } else
        {
            holder = (BookingsListItemAdapter.ViewHolder) view.getTag();
        }


        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String grievanceDateString = dateFormatter.format(bookings.get(position).getDate().getTime());
        holder.dateTV.setText("DATE: " + grievanceDateString);
        AppDatabaseHelper databaseHelper = new AppDatabaseHelper(context);
        CloakRoom cloakRoom = databaseHelper.getCloakRoomWithID(bookings.get(position).getCloakRoomID());
        holder.cloakRoomTV.setText(cloakRoom.getName());
        Tourist tourist = ((AppInstance)context.getApplicationContext()).getCurrentTourist();
        if (tourist == null)
        {
            tourist = databaseHelper.getTouristWithID(bookings.get(position).getTouristID());
        }
        holder.touristTV.setText("TOURIST: " + tourist.getName());

        String imagePath = cloakRoom.getProfilePath();
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        if (null != bitmap) {
            holder.imageIV.setImageBitmap(bitmap);
        }
        else
        {
            int resID = context.getResources().getIdentifier("noimage", Constants.DRAWABLE_RESOURCE, context.getPackageName());
            holder.imageIV.setImageResource(resID);
        }


        return view;
    }

    public List<Booking> getBookings() {
        return this.bookings;
    }

    public void setBookings(List<Booking> bookings)
    {
        this.bookings = bookings;
    }

    @Override
    public int getCount() {
        return bookings.size();
    }

    @Override
    public Booking getItem(int position) {
        return bookings.get(position);
    }

}
