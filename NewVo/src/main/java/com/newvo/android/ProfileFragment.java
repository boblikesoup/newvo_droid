package com.newvo.android;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.newvo.android.parse.Post;
import com.newvo.android.remote.CurrentUserProfileRequest;
import com.newvo.android.util.LoadingFragment;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

/**
 * Created by David on 4/20/2014.
 */
public class ProfileFragment extends Fragment implements LoadingFragment {

    private ProfileViewHolder holder;

    private List<Post> activePosts;
    private List<Post> inactivePosts;

    public static Post selectedPost;

    public ProfileFragment() {
        requestPosts(Post.ACTIVE);
        requestPosts(Post.INACTIVE);
    }

    private void requestPosts(String active){
        if(active.equals(Post.ACTIVE)){
            new CurrentUserProfileRequest().request(Post.ACTIVE, new FindCallback<Post>() {
                @Override
                public void done(List<Post> posts, ParseException e) {
                    if(e == null) {
                        activePosts = posts;
                        if(holder != null){
                            holder.populateListView(Post.ACTIVE, posts);
                        } else {
                            Activity activity = getActivity();
                            if (activity != null) {
                                ((DrawerActivity)getActivity()).attachDetachFragment();
                            }
                        }
                    } else {
                        requestPosts(Post.ACTIVE);
                    }
                }
            });
        } else {
            new CurrentUserProfileRequest().request(Post.INACTIVE, new FindCallback<Post>() {
                @Override
                public void done(List<Post> posts, ParseException e) {
                    if(e == null) {
                        inactivePosts = posts;
                        if(holder != null){
                            holder.populateListView(Post.INACTIVE, posts);
                        } else {
                            Activity activity = getActivity();
                            if (activity != null) {
                                ((DrawerActivity)getActivity()).attachDetachFragment();
                            }
                        }
                    } else {
                        requestPosts(Post.INACTIVE);
                    }
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        if(activePosts == null || inactivePosts == null){
            rootView = inflater.inflate(R.layout.text, container, false);
        } else if(activePosts.size() == 0 && inactivePosts.size() == 0){
            ((DrawerActivity)getActivity()).setActionBarLoading(false);
            rootView = inflater.inflate(R.layout.text, container, false);
            final TextView text = (TextView) rootView.findViewById(R.id.text);
            text.setText("There seem to be no posts here. Go create one...");
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() != null) {
                        ((DrawerActivity)getActivity()).displayView(getString(R.string.title_create_post));
                    }
                }
            });
        } else {
            ((DrawerActivity)getActivity()).setActionBarLoading(false);
            rootView = inflater.inflate(R.layout.profile, container, false);
            if (holder == null) {
                holder = new ProfileViewHolder(rootView, activePosts, inactivePosts);
            } else {
                holder.setView(rootView);
            }

            //When tabbing back, select the tab the post was on.
            if (selectedPost != null) {
                if (inactivePosts.contains(selectedPost)) {
                    holder.setCurrentItem(1);
                }
            }
            selectedPost = null;

            return rootView;
        }
        return rootView;
    }

    @Override
    public boolean hasLoaded() {
        return activePosts != null && inactivePosts != null;
    }
}
