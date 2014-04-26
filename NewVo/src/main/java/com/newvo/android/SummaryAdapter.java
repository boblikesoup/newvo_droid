package com.newvo.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.newvo.android.parse.Post;

/**
 * Created by David on 4/20/2014.
 */
public class SummaryAdapter extends ArrayAdapter<Post> {

    public SummaryAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SummaryViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.summary, null);
            holder = new SummaryViewHolder(getContext(), convertView);
            convertView.setTag(holder);
        } else {
            holder = (SummaryViewHolder) convertView.getTag();
        }

        Post item = getItem(position);
        holder.setItem(item);

        return convertView;
    }

}
