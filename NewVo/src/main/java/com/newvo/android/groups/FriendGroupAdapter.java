package com.newvo.android.groups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.facebook.model.GraphUser;
import com.newvo.android.R;
import com.newvo.android.friends.FriendPickerActivity;

/**
 * Created by David on 6/25/2014.
 */
public class FriendGroupAdapter extends ArrayAdapter<GraphUser> {

    public FriendGroupAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.suggestion_single, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GraphUser item = getItem(position);
        holder.setItem(item);

        return convertView;
    }

    class ViewHolder {

        @InjectView(R.id.suggestion_text)
        TextView suggestionText;
        @InjectView(R.id.suggestion_x)
        ImageButton suggestionX;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }


        public void setItem(final GraphUser item) {
            suggestionText.setText(item.getName());
            suggestionX.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FriendPickerActivity.SELECTION.remove(item);
                    remove(item);
                }
            });
        }
    }
}
