package com.newvo.android;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.astuetz.PagerSlidingTabStrip;
import com.newvo.android.parse.Post;
import com.newvo.android.remote.CurrentUserProfileRequest;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

/**
 * Created by David on 4/20/2014.
 */
public class ProfileFragment extends Fragment {

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
                        populateListView(Post.ACTIVE, posts);
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
                        populateListView(Post.INACTIVE, posts);
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
        if(activePosts == null && inactivePosts == null){
            rootView = inflater.inflate(R.layout.text, container, false);
        } else if((activePosts == null || activePosts.size() == 0) &&
                (inactivePosts == null || inactivePosts.size() == 0)){
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
            rootView = inflater.inflate(R.layout.profile, container, false);
            if (holder == null) {
                holder = new ProfileViewHolder(rootView);
            } else {
                holder.setView(rootView);
            }

            if (activePosts != null) {
                for (Post post : activePosts) {
                    changeLists(post);
                }
            }
            if (inactivePosts != null) {
                for (Post post : inactivePosts) {
                    changeLists(post);
                }
            }

            if (activePosts != null) {
                holder.populateListView(Post.ACTIVE, activePosts);
            }
            if (inactivePosts != null) {
                holder.populateListView(Post.INACTIVE, inactivePosts);
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


    private void changeLists(Post post){
        if(post != null){
            if(post.getStatus().equals(Post.INACTIVE) &&
                    activePosts.contains(post)){
                activePosts.remove(post);
                inactivePosts.add(post);
                if(inactiveList != null && activeList != null){
                    ((ArrayAdapter)activeList.getAdapter()).remove(post);
                    ((ArrayAdapter)inactiveList.getAdapter()).add(post);
                }
            } else if(post.getStatus().equals(Post.ACTIVE) &&
                    inactivePosts.contains(post)){
                inactivePosts.remove(post);
                activePosts.add(post);
                if(inactiveList != null && activeList != null){
                    ((ArrayAdapter)inactiveList.getAdapter()).remove(post);
                    ((ArrayAdapter)activeList.getAdapter()).add(post);
                }
            }
        }
    }
}
