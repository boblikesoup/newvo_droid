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
import com.parse.SaveCallback;

/**
 * Created by David on 4/20/2014.
 */
public class SummaryAdapter extends ArrayAdapter<Post> {

    private String active;
    private final EditPostCallback editPostCallback;

    public SummaryAdapter(Context context, int resource, EditPostCallback editPostCallback) {
        super(context, resource);
        this.editPostCallback = editPostCallback;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SummaryViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.summary, parent, false);
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
        }, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                editPostCallback.editPost(item);
            }
        });

        return convertView;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getActive() {
        return active;
    }

    public static abstract class EditPostCallback {

        public abstract void editPost(Post post);
    }
}
