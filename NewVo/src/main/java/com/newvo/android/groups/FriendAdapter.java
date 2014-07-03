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

import java.util.List;

/**
 * Created by David on 6/25/2014.
 */
public class FriendAdapter extends ArrayAdapter<GraphUser> {

    private final int resource;
    private final boolean writeAccess;
    private final List<GraphUser> friends;

    public FriendAdapter(Context context, int resource, boolean writeAccess) {
        this(context, FriendPickerActivity.SELECTION, resource, writeAccess);
    }

    public FriendAdapter(Context context, List<GraphUser> friends, int resource, boolean writeAccess) {
        super(context, resource);
        this.friends = friends;
        this.resource = resource;
        this.writeAccess = writeAccess;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
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

        @InjectView(R.id.text)
        TextView text;
        @InjectView(R.id.x)
        ImageButton x;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }


        public void setItem(final GraphUser item) {
            text.setText(item.getName());
            if(writeAccess) {
                x.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        friends.remove(item);
                        remove(item);
                    }
                });
            } else {
                x.setVisibility(View.GONE);
            }
        }
    }
}
