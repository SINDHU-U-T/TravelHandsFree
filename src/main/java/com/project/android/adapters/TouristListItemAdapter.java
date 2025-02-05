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
import com.project.android.model.Feedback;
import com.project.android.model.Tourist;
import com.project.android.utility.Constants;

import java.util.List;

public class TouristListItemAdapter extends ArrayAdapter<Tourist> {
    private Activity context;
    List<Tourist> touristList;
    LayoutInflater inflater;

    public TouristListItemAdapter(Activity context, int resourceId)
    {
        super(context, resourceId);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder
    {
        TextView nameTV;
        ImageView iconIV;
    }

    public View getView(int position, View view, ViewGroup parent) {

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.alltouristlist_item, null);
            holder.nameTV = view.findViewById(R.id.name);
            holder.iconIV = view.findViewById(R.id.tourist);

            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }
        holder.nameTV.setText(touristList.get(position).getName());
        String imagePath = touristList.get(position).getProfilePath();
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        if (null != bitmap) {
            holder.iconIV.setImageBitmap(bitmap);
        }
        else
        {
            int resID = context.getResources().getIdentifier("noimage", Constants.DRAWABLE_RESOURCE, context.getPackageName());
            holder.iconIV.setImageResource(resID);
        }

        return view;
    }

    public List<Tourist> getTouristList() {
        return touristList;
    }

    public void setTouristList(List<Tourist> touristList)
    {
        this.touristList = touristList;
    }

    @Override
    public int getCount() {
        return touristList.size();
    }

    @Override
    public Tourist getItem(int position) {
        return touristList.get(position);
    }

}


