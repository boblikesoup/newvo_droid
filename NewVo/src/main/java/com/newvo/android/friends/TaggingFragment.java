package com.newvo.android.friends;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.facebook.model.GraphUser;
import com.newvo.android.R;
import com.newvo.android.groups.FriendAdapter;
import com.newvo.android.util.ChildFragment;
import com.newvo.android.util.IntentUtils;

import java.util.List;

/**
 * Created by David on 6/28/2014.
 */
public class TaggingFragment extends Fragment implements ChildFragment {

    @InjectView(R.id.add_friend)
    View addFriend;

    @InjectView(R.id.friends_list)
    ListView friends;

    @InjectView(R.id.add_group)
    View addGroup;

    @InjectView(R.id.groups_list)
    ListView groups;

    public TaggingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tagging, container, false);
        ButterKnife.inject(this, rootView);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startFriendPickerIntent(TaggingFragment.this);
            }
        });

        if(FriendPickerActivity.SELECTION != null){
            initFriendAdapter();
        }

        return rootView;
    }

    private void initFriendAdapter() {
        FriendAdapter adapter = new FriendAdapter(getActivity(), R.layout.suggestion_single, true);
        adapter.addAll(FriendPickerActivity.SELECTION);
        friends.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IntentUtils.FRIEND_PICKER){
            List<GraphUser> selection = FriendPickerActivity.SELECTION;
            friends.setAdapter(new FriendAdapter(getActivity(), R.layout.suggestion_single, true));
            if(selection != null){
                ((FriendAdapter)friends.getAdapter()).addAll(selection);
            }
        }
    }
}
