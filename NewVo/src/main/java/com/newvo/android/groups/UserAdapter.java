package com.newvo.android.groups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.R;
import com.newvo.android.parse.User;

/**
 * Created by David on 6/27/2014.
 */
public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(Context context, int resource) {
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

        User item = getItem(position);
        holder.setItem(item);

        return convertView;
    }

    class ViewHolder {

        @InjectView(R.id.text)
        TextView text;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }


        public void setItem(final User item) {
            text.setText(item.getPublicName());
        }
    }
}
