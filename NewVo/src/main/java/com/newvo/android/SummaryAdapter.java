package com.newvo.android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.newvo.android.parse.Post;
import com.parse.DeleteCallback;
import com.parse.ParseException;

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

        final Post item = getItem(position);
        holder.setItem(item, new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    SummaryAdapter.this.remove(item);
                } else {
                    Log.e("NewVo", "Failed to remove suggestion.");
                }
            }
        });

        return convertView;
    }

}
