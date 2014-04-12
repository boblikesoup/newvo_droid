package com.newvo.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by David on 4/11/2014.
 */
public class ComparisonAdapter extends ArrayAdapter {

    public ComparisonAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = NewVo.inflate(R.layout.comparison, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText("testname");

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.name)
        TextView name;
        @InjectView(R.id.question)
        TextView question;
        @InjectView(R.id.user_icon)
        ImageButton userIcon;
        @InjectView(R.id.first_image)
        ImageView firstImage;
        @InjectView(R.id.second_image)
        ImageView secondImage;
        @InjectView(R.id.number_of_comments)
        TextView numberOfComments;
        @InjectView(R.id.expand_button)
        ImageButton expandButton;
        @InjectView(R.id.first_choice)
        ImageButton firstChoice;
        @InjectView(R.id.second_choice)
        ImageButton secondChoice;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
