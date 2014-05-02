package com.newvo.android;

import android.app.Fragment;
import android.content.Intent;
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

/**
 * Created by David on 4/13/2014.
 */
public class HomeFragment extends Fragment {

    private List<Post> posts;
    private int location = -1;

    private ComparisonViewHolder holder;

    public HomeFragment() {
        requestMorePosts();
    }

    private OnActivityResult onActivityResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.comparison, container, false);
        ButterKnife.inject(this, rootView);
        if(holder == null){
            holder = new ComparisonViewHolder(rootView, this);
            onActivityResult = holder.getOnActivityResult();

        } else {
            holder.setView(rootView);
        }
        if (posts != null) {
            if ((holder.hasVoted() || location == -1)) {
                loadNextPost();
            } else {
                loadPost();
            }
        }
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(onActivityResult != null){
            onActivityResult.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setOnActivityResult(OnActivityResult onActivityResult) {
        this.onActivityResult = onActivityResult;
    }

    private void loadNextPost(){
        location++;
        if(location > 2){
            requestMorePosts();
        }
        loadPost();
    }

    private void loadPost(){
        if(!posts.isEmpty() && location < posts.size()){
                holder.setItem(posts.get(location));
        } else {
            //TODO: You have read all of the posts, you wizard.
        }
    }

    private void requestMorePosts(){
        new FeedRequest().request(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(posts != null){
                    HomeFragment.this.posts = posts;
                    location = 0;
                    if(holder != null && holder.getPost() == null){
                        loadPost();
                    }
                }

            }
        });
    }

    public static abstract class OnActivityResult {
        public abstract void onActivityResult(int requestCode, int resultCode, Intent data);
    }

}
