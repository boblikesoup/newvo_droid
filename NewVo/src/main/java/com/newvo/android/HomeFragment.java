package com.newvo.android;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.newvo.android.parse.Post;
import com.newvo.android.remote.FeedRequest;
import com.newvo.android.util.LoadingFragment;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;
import java.util.TreeSet;

/**
 * Created by David on 4/13/2014.
 */
public class HomeFragment extends Fragment implements LoadingFragment {

    private TreeSet<Post> posts = new TreeSet<Post>();

    private ComparisonViewHolder holder;

    private boolean loadingPosts = true;

    public HomeFragment() {
        requestMorePosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (posts != null) {
            View rootView = inflater.inflate(R.layout.comparison, container, false);
            ButterKnife.inject(this, rootView);
            if (holder == null) {
                holder = new ComparisonViewHolder(rootView, this);
            } else {
                holder.setView(rootView);
            }
            if ((holder.getLastVotedPost() != null)) {
                loadNextPost();
            } else {
                loadPost();
            }
            if(posts.size() != 0) {
                ((DrawerActivity)getActivity()).setActionBarLoading(false);
                return rootView;
            }
        }
        View rootView = inflater.inflate(R.layout.text, container, false);
        final TextView text = (TextView) rootView.findViewById(R.id.text);
        if(!loadingPosts){
            text.setText("You wizard! You have voted on every post!");
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingPosts = true;
                    ((DrawerActivity)getActivity()).setActionBarLoading(true);
                    text.setText("Loading...");
                    requestMorePosts();
                }
            });
        }
        return rootView;
    }

    private void loadNextPost() {
        if (posts.size() > 0 && holder != null && holder.getLastVotedPost() != null) {
            posts.remove(holder.getLastVotedPost());
        }
        if (posts.size() < 5) {
            requestMorePosts();
        }
        loadPost();
    }

    private void loadPost() {
        if (!posts.isEmpty()) {
            holder.setItem(posts.first());
        }
    }

    private void requestMorePosts() {
        new FeedRequest().request(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e == null) {
                    //Remove already voted on posts.
                    if (posts != null) {
                        if (holder != null) {
                            int size = posts.size();
                            for (Post post : holder.getVotedPosts()) {
                                if (posts.contains(post)) {
                                    posts.remove(post);
                                }
                            }
                            if (posts.size() == 0) {
                                loadingPosts = false;
                                if (size == FeedRequest.NUMBER_OF_POSTS) {
                                    requestMorePosts();
                                } else {
                                    Activity activity = getActivity();
                                    if (activity != null) {
                                        ((DrawerActivity)getActivity()).attachDetachFragment();
                                    }
                                }
                                return;
                            }
                            HomeFragment.this.posts.addAll(posts);
                            if (holder.getPost() == null) {
                                loadPost();
                            }
                        }
                    }
                    if(loadingPosts){
                        loadingPosts = false;
                        Activity activity = getActivity();
                        if (activity != null) {
                            ((DrawerActivity)getActivity()).attachDetachFragment();
                        }
                    }
                } else {
                    requestMorePosts();
                }
            }
        });
    }

    @Override
    public boolean hasLoaded() {
        return !loadingPosts;
    }
}
