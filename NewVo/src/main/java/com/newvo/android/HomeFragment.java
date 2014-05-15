package com.newvo.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.newvo.android.parse.Post;
import com.newvo.android.remote.FeedRequest;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;
import java.util.TreeSet;

/**
 * Created by David on 4/13/2014.
 */
public class HomeFragment extends Fragment {

    private TreeSet<Post> posts = new TreeSet<Post>();

    private ComparisonViewHolder holder;

    public HomeFragment() {
        requestMorePosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.comparison, container, false);
        ButterKnife.inject(this, rootView);
        if(holder == null){
            holder = new ComparisonViewHolder(rootView, this);

        } else {
            holder.setView(rootView);
        }
        if (posts != null) {
            if ((holder.getLastVotedPost() != null)) {
                loadNextPost();
            } else {
                loadPost();
            }
        }
        return rootView;
    }

    private void loadNextPost(){
        if(posts.size() > 0 && holder != null && holder.getLastVotedPost() != null) {
            posts.remove(holder.getLastVotedPost());
        }
        if(posts.size() < 5){
            requestMorePosts();
        }
        loadPost();
    }

    private void loadPost(){
        if(!posts.isEmpty()){
                holder.setItem(posts.first());
        } else {
            //TODO: You have read all of the posts, you wizard.
        }
    }

    private void requestMorePosts(){
        new FeedRequest().request(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                //Remove already voted on posts.
                if(holder != null){
                    int size = posts.size();
                    for(Post post : holder.getVotedPosts()){
                        if(posts.contains(post)){
                            posts.remove(post);
                        }
                    }
                    if(posts.size() == 0 && size == FeedRequest.NUMBER_OF_POSTS){
                        requestMorePosts();
                    }
                }
                if(posts != null){
                    HomeFragment.this.posts.addAll(posts);
                    if(holder != null && holder.getPost() == null){
                        loadPost();
                    }
                }

            }
        });
    }

}
