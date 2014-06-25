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
import com.facebook.model.GraphUser;
import com.newvo.android.R;
import com.newvo.android.friends.FriendPickerActivity;
import com.newvo.android.util.ChildFragment;
import com.newvo.android.util.IntentUtils;

import java.util.List;

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

        friendsToAdd.setAdapter(new FriendGroupAdapter(getActivity(), R.layout.suggestion_single));

        addMoreFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startFriendPickerIntent(CreateGroupFragment.this);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IntentUtils.FRIEND_PICKER){
            List<GraphUser> selection = FriendPickerActivity.SELECTION;
            if(selection != null){
                ((FriendGroupAdapter)friendsToAdd.getAdapter()).addAll(selection);
            }
        }
    }
}
