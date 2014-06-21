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

    private EditPostCallback editPostCallback;

    public SummaryAdapter(Context context, int resource) {
        super(context, resource);
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
        holder.setDeleteCallback(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    SummaryAdapter.this.remove(item);
                } else {
                    Log.e("NewVo", "Failed to remove suggestion.");
                }
            }
        });
        holder.setSaveCallback(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(editPostCallback != null){
                    editPostCallback.editPost(item);
                }
            }
        });
        holder.setItem(item);

        return convertView;
    }

    public EditPostCallback getEditPostCallback() {
        return editPostCallback;
    }

    public void setEditPostCallback(EditPostCallback editPostCallback) {
        this.editPostCallback = editPostCallback;
    }

    public static abstract class EditPostCallback {

        public abstract void editPost(Post post);
    }
}
