package com.newvo.android.groups;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.facebook.model.GraphUser;
import com.newvo.android.NewVoActivity;
import com.newvo.android.R;
import com.newvo.android.friends.FriendPickerActivity;
import com.newvo.android.parse.User;
import com.newvo.android.remote.CreateGroupRequest;
import com.newvo.android.util.ChildFragment;
import com.newvo.android.util.IntentUtils;
import com.newvo.android.util.ToastUtils;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by David on 6/22/2014.
 */
public class CreateGroupFragment extends Fragment implements ChildFragment {

    public static final int DP_OFFSET = 40;

    @InjectView(R.id.group_name)
    EditText groupName;

    @InjectView(R.id.group_description)
    EditText groupDescription;

    @InjectView(R.id.add_more_friends)
    Button addMoreFriends;

    @InjectView(R.id.friends_to_add)
    ListView friendsToAdd;

    @InjectView(R.id.create_group)
    Button createGroup;

    private List<User> users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.create_group, container, false);
        ButterKnife.inject(this, rootView);

        friendsToAdd.setAdapter(new FriendAdapter(getActivity(), R.layout.suggestion_single));

        addMoreFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startFriendPickerIntent(CreateGroupFragment.this);
            }
        });

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Activity activity = getActivity();
                CharSequence text = groupName.getText();
                String name;
                if (text == null) {
                    name = null;
                } else {
                    name = text.toString();
                }
                text = groupDescription.getText();
                String description;
                if (text == null) {
                    description = null;
                } else {
                    description = text.toString();
                }
                try {
                    new CreateGroupRequest(name,
                            description,
                            ((FriendAdapter)friendsToAdd.getAdapter()).getFriends()).request(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                ToastUtils.makeText(activity, "Could Not Create Group", Toast.LENGTH_LONG, DP_OFFSET).show();
                            } else {
                                ToastUtils.makeText(activity, "Group Created Successfully!", Toast.LENGTH_LONG, DP_OFFSET).show();
                                activity.onBackPressed();
                                ((NewVoActivity)activity).restartFragment();
                            }
                        }
                    });
                } catch (CreateGroupRequest.MissingTitleError missingTitleError) {
                    ToastUtils.makeText(activity, "Needs a Group Name", Toast.LENGTH_LONG, DP_OFFSET).show();
                }
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
                ((FriendAdapter)friendsToAdd.getAdapter()).addAll(selection);
            }
        }
    }
}
