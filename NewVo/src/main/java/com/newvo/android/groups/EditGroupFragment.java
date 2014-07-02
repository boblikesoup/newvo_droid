package com.newvo.android.groups;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
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
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.newvo.android.DrawerActivity;
import com.newvo.android.NewVoActivity;
import com.newvo.android.R;
import com.newvo.android.friends.FriendPickerActivity;
import com.newvo.android.parse.Group;
import com.newvo.android.parse.User;
import com.newvo.android.remote.EditGroupRequest;
import com.newvo.android.util.ChildFragment;
import com.newvo.android.util.IntentUtils;
import com.newvo.android.util.LoadingFragment;
import com.newvo.android.util.ToastUtils;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 6/22/2014.
 */
public class EditGroupFragment extends Fragment implements ChildFragment, LoadingFragment {

    public static final int DP_OFFSET = 40;

    private final boolean writeAccess;

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

    private Group group;
    private String successText;
    private String failureText;

    public EditGroupFragment(Group group){
        writeAccess = group == null || group.getACL().getWriteAccess(User.getCurrentUser());
        if(writeAccess) {
            FriendPickerActivity.SELECTION = null;
        }
        this.group = group;
        if(group != null){
            requestUsers(group);
        }
    }

    public EditGroupFragment(Group group, boolean writeAccess) {
        this.group = group;
        this.writeAccess = writeAccess;
        if(group != null){
            requestUsers(group);
        }
    }

    public void requestUsers(final Group group) {
        Request.newMyFriendsRequest(Session.getActiveSession(), new Request.GraphUserListCallback() {
            @Override
            public void onCompleted(List<GraphUser> graphUsers, Response response) {
                List<String> memberIds = group.getMemberIds();
                List<GraphUser> members = new ArrayList<GraphUser>();
                for(String memberId : memberIds){
                    for(GraphUser graphUser : graphUsers){
                        if(memberId.equals(graphUser.getId())){
                            members.add(graphUser);
                        }
                    }
                }
                FriendPickerActivity.SELECTION = members;
                if(friendsToAdd != null && friendsToAdd.getAdapter() == null){
                    initFriendAdapter(writeAccess);
                }
            }
        }).executeAsync();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.create_group, container, false);
        ButterKnife.inject(this, rootView);

        Activity activity = getActivity();
        if(activity != null) {
            ((DrawerActivity) activity).setActionBarLoading(true);
        }

        if(FriendPickerActivity.SELECTION != null){
            initFriendAdapter(writeAccess);
        }

        successText = "Group Created Successfully!";
        failureText = "Could Not Create Group";

        if(group != null){
            groupName.setText(group.getTitle());
            groupDescription.setText(group.getDescription());
            createGroup.setText("Edit Group");

            successText = "Group Edited Successfully!";
            failureText = "Could Not Edit Group";
        }

        if(writeAccess) {

            addMoreFriends.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtils.startFriendPickerIntent(EditGroupFragment.this);
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
                        if(FriendPickerActivity.SELECTION == null || FriendPickerActivity.SELECTION.isEmpty()){
                            ToastUtils.makeText(activity, "Please Add Friends To Your Group", Toast.LENGTH_LONG, DP_OFFSET).show();
                            return;
                        }
                        ((DrawerActivity) getActivity()).setActionBarLoading(true);
                        new EditGroupRequest(group, name,
                                description,
                                FriendPickerActivity.SELECTION).request(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(EditGroupFragment.this.equals( ((NewVoActivity) activity).getActiveFragment())) {
                                    ((DrawerActivity) activity).setActionBarLoading(false);
                                }
                                if (e != null) {
                                    ToastUtils.makeText(activity, failureText, Toast.LENGTH_LONG, DP_OFFSET).show();
                                } else {
                                    ToastUtils.makeText(activity, successText, Toast.LENGTH_LONG, DP_OFFSET).show();
                                    if(EditGroupFragment.this.equals( ((NewVoActivity) activity).getActiveFragment())) {
                                        activity.onBackPressed();
                                    }

                                }
                            }
                        });
                    } catch (EditGroupRequest.MissingTitleError missingTitleError) {
                        ((DrawerActivity) getActivity()).setActionBarLoading(false);
                        ToastUtils.makeText(activity, "Needs a Group Name", Toast.LENGTH_LONG, DP_OFFSET).show();
                    }
                }
            });
        } else {
            groupName.setEnabled(false);
            groupDescription.setEnabled(false);
            addMoreFriends.setVisibility(View.GONE);
            createGroup.setVisibility(View.GONE);
            groupName.setTextColor(Color.WHITE);
            groupDescription.setTextColor(Color.WHITE);
        }

        return rootView;
    }

    private void initFriendAdapter(boolean writeAccess) {
        FriendAdapter adapter = new FriendAdapter(getActivity(), R.layout.suggestion_single, writeAccess);
        adapter.addAll(FriendPickerActivity.SELECTION);
        friendsToAdd.setAdapter(adapter);

        Activity activity = getActivity();
        if(activity != null) {
            ((DrawerActivity) activity).setActionBarLoading(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IntentUtils.FRIEND_PICKER){
            List<GraphUser> selection = FriendPickerActivity.SELECTION;
            friendsToAdd.setAdapter(new FriendAdapter(getActivity(), R.layout.suggestion_single, writeAccess));
            if(selection != null){
                ((FriendAdapter)friendsToAdd.getAdapter()).addAll(selection);
            }
        }
    }

    @Override
    public boolean hasLoaded() {
        return FriendPickerActivity.SELECTION != null;
    }
}
