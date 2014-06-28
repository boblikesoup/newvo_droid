package com.newvo.android.groups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.NewVoActivity;
import com.newvo.android.R;
import com.newvo.android.parse.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 6/25/2014.
 */
public class GroupAdapter extends ArrayAdapter<Group> {

    public GroupAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.group_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Group item = getItem(position);
        holder.setItem(item);

        return convertView;
    }

    public List<Group> getGroups() {
        List<Group> friends = new ArrayList<Group>();
        for(int i = 0; i < getCount(); i++){
            friends.add(getItem(i));
        }
        return friends;
    }

    class ViewHolder {

        @InjectView(R.id.layout)
        View layout;

        @InjectView(R.id.text)
        TextView text;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }


        public void setItem(final Group item) {
            text.setText(item.getTitle());
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((NewVoActivity) getContext()).displayChildFragment(new GroupFragment(item), getContext().getString(R.string.title_group), "SingleGroup");
                }
            });
        }
    }
}
