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

    private EditPostCallback saveCallback;
    private EditPostCallback openSuggestionsCallback;

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
        holder.setSaveCallback(saveCallback);
        holder.setItem(item);

        if (item != null && item.getNumberOfSuggestions() != 0 && getContext() instanceof DrawerActivity) {
            holder.suggestionsIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DrawerActivity) getContext()).displayChildFragment(new SuggestionsFragment(item), getContext().getString(R.string.title_suggestions), "SuggestionsList");
                    if(openSuggestionsCallback != null) {
                        openSuggestionsCallback.editPost(item);
                    }
                }
            });
        }

        return convertView;
    }

    public EditPostCallback getSaveCallback() {
        return saveCallback;
    }

    public void setSaveCallback(EditPostCallback saveCallback) {
        this.saveCallback = saveCallback;
    }

    public EditPostCallback getOpenSuggestionsCallback() {
        return openSuggestionsCallback;
    }

    public void setOpenSuggestionsCallback(EditPostCallback openSuggestionsCallback) {
        this.openSuggestionsCallback = openSuggestionsCallback;
    }
}
