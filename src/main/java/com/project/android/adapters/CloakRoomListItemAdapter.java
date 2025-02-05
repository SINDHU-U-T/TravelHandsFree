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
import com.project.android.model.CloakRoom;
import com.project.android.model.Tourist;
import com.project.android.utility.Constants;

import java.util.List;

public class CloakRoomListItemAdapter extends ArrayAdapter<CloakRoom> {
    private Activity context;
    List<CloakRoom> cloakRoomList;
    LayoutInflater inflater;

    public CloakRoomListItemAdapter(Activity context, int resourceId)
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
            view = inflater.inflate(R.layout.allcloakroomlist_item, null);
            holder.nameTV = view.findViewById(R.id.name);
            holder.iconIV = view.findViewById(R.id.cloakroom);

            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }
        holder.nameTV.setText(cloakRoomList.get(position).getName());
        String imagePath = cloakRoomList.get(position).getProfilePath();
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

    public List<CloakRoom> getCloakRoomList() {
        return cloakRoomList;
    }

    public void setCloakRoomList(List<CloakRoom> cloakRoomList)
    {
        this.cloakRoomList = cloakRoomList;
    }

    @Override
    public int getCount() {
        return cloakRoomList.size();
    }

    @Override
    public CloakRoom getItem(int position) {
        return cloakRoomList.get(position);
    }

}


