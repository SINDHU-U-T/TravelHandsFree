package com.project.android.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.project.android.R;
import com.project.android.model.Feedback;

public class FeedbackListItemAdapter extends ArrayAdapter<Feedback> {
    private Activity context;
    List<Feedback> feedbackList;
    LayoutInflater inflater;

    public FeedbackListItemAdapter(Activity context, int resourceId)
    {
        super(context, resourceId);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder
    {
        TextView descriptionTV;
    }

    public View getView(int position, View view, ViewGroup parent) {

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.allfeedbacklist_item, null);
            holder.descriptionTV = view.findViewById(R.id.description);
            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }
        holder.descriptionTV.setText(feedbackList.get(position).getDescription());

        return view;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList)
    {
        this.feedbackList = feedbackList;
    }

    @Override
    public int getCount() {
        return feedbackList.size();
    }

    @Override
    public Feedback getItem(int position) {
        return feedbackList.get(position);
    }

}


