package com.newvo.android.groups;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.NewVoActivity;
import com.newvo.android.R;
import com.newvo.android.SummaryAdapter;
import com.newvo.android.parse.Group;
import com.newvo.android.parse.Post;
import com.newvo.android.parse.User;
import com.newvo.android.remote.GroupProfileRequest;
import com.newvo.android.util.ChildFragment;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

/**
 * Created by David on 6/26/2014.
 */
public class GroupFragment extends Fragment implements ChildFragment {

    @InjectView(R.id.group_name)
    TextView groupName;

    @InjectView(R.id.settings)
    ImageButton settings;

    @InjectView(R.id.posts)
    ListView posts;

    private Group group;
    private List<Post> postList;

    public GroupFragment(Group group) {
        this.group = group;
        requestPosts();
    }


    private void requestPosts() {
        new GroupProfileRequest(group).request(new FindCallback<Post>() {

            @Override
            public void done(List<Post> postList, ParseException e) {
                if (e == null) {
                    GroupFragment.this.postList = postList;
                    if (posts != null) {
                        ((ArrayAdapter<Post>) posts.getAdapter()).addAll(postList);
                    }
                } else {
                    requestPosts();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.group, container, false);
        ButterKnife.inject(this, rootView);

        groupName.setText(group.getTitle());

        posts.setAdapter(new SummaryAdapter(getActivity(), R.layout.summary));

        final PopupMenu popupMenu = new PopupMenu(getActivity(), settings);
        boolean writeAccess = group.getACL().getWriteAccess(User.getCurrentUser());


        //Populate popup with available choices
        popupMenu.getMenu().add(0, 0, 0, "Create Post");
        popupMenu.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //TODO: When tagging has been implemented, add this.
         //       ((NewVoActivity) getActivity()).displayChildFragment(new CreatePostFragment(group), getActivity().getString(R.string.title_create_post), "CreateGroupPost");
                return true;
            }
        });
        popupMenu.getMenu().add(0, 0, 1, "Manage Notification Settings");
        popupMenu.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //TODO: Manage Notification Settings
                return true;
            }
        });

        if (writeAccess) {
            popupMenu.getMenu().add(0, 0, 1, "Edit Group");
        } else {
            popupMenu.getMenu().add(0, 0, 1, "View Group");
            popupMenu.getMenu().add(0, 0, 3, "Leave Group");
            //TODO: Leave Group
        }
        //Edit/View Group
        popupMenu.getMenu().getItem(2).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((NewVoActivity) getActivity()).displayChildFragment(new EditGroupFragment(group), getActivity().getString(R.string.title_group), "EditGroup");
                return true;
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });

        if (postList != null) {
            ((ArrayAdapter<Post>) posts.getAdapter()).addAll(postList);
        }


        return rootView;
    }
}
