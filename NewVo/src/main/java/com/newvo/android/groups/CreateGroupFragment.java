package com.newvo.android.groups;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.R;
import com.newvo.android.friends.FriendPickerActivity;
import com.newvo.android.util.ChildFragment;

/**
 * Created by David on 6/22/2014.
 */
public class CreateGroupFragment extends Fragment implements ChildFragment {

    @InjectView(R.id.group_name)
    EditText groupName;

    @InjectView(R.id.group_description)
    EditText groupDescription;

    @InjectView(R.id.add_more_friends)
    Button addMoreFriends;


    @InjectView(R.id.friends_to_add)
    ListView friendsToAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.create_group, container, false);
        ButterKnife.inject(this, rootView);

        addMoreFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendPickerActivity.class);
                //... put other settings in the Intent
                CreateGroupFragment.this.startActivityForResult(intent, 1500);
            }
        });

        return rootView;
    }
}
