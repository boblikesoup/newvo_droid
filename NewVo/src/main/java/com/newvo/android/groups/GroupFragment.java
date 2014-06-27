package com.newvo.android.groups;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.R;
import com.newvo.android.SummaryAdapter;
import com.newvo.android.parse.Group;
import com.newvo.android.parse.Post;
import com.newvo.android.remote.GroupProfileRequest;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

/**
 * Created by David on 6/26/2014.
 */
public class GroupFragment extends Fragment {

    @InjectView(R.id.group_name)
    TextView groupName;

    @InjectView(R.id.settings)
    ImageButton settings;

    @InjectView(R.id.posts)
    ListView posts;

    private Group group;
    private List<Post> postList;

    public GroupFragment(Group group){
        this.group = group;
        requestPosts();
    }


    private void requestPosts(){
        new GroupProfileRequest(group).request(new FindCallback<Post>() {

            @Override
            public void done(List<Post> postList, ParseException e) {
                if(e == null){
                    GroupFragment.this.postList = postList;
                    if(posts != null){
                        ((ArrayAdapter<Post>)posts.getAdapter()).addAll(postList);
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

        if(postList != null){
            ((ArrayAdapter<Post>)posts.getAdapter()).addAll(postList);
        }


        return rootView;
    }
}
