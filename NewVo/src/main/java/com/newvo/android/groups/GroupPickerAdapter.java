package com.newvo.android.groups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.NewVoActivity;
import com.newvo.android.R;
import com.newvo.android.parse.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 6/29/2014.
 */
public class GroupPickerAdapter extends ArrayAdapter<Group> {

    public static List<Group> SELECTION;

    public GroupPickerAdapter(Context context, int resource) {
        super(context, resource);
        if(SELECTION == null) {
            SELECTION = new ArrayList<Group>();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.picker_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Group item = getItem(position);
        holder.setItem(item);

        return convertView;
    }

    class ViewHolder {

        @InjectView(R.id.picker_layout)
        View layout;

        @InjectView(R.id.picker_text)
        TextView text;

        @InjectView(R.id.picker_checkbox)
        CheckBox checkbox;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }


        public void setItem(final Group item) {
            text.setText(item.getTitle());
            if(SELECTION.contains(item)){
                checkbox.setChecked(true);
            }
            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkbox.setChecked(toggleGroupSelected(item));
                }
            });
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((NewVoActivity) getContext()).displayChildFragment(new EditGroupFragment(item, false), getContext().getString(R.string.title_create_post), "EditGroup");
                }
            });
        }
    }

    private boolean toggleGroupSelected(Group group){
        boolean contains = SELECTION.contains(group);
        if(contains){
            SELECTION.remove(group);
        } else {
            SELECTION.add(group);
        }
        return !contains;
    }
}
