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

    private Profile profile = new Profile();

    public ProfileFragment() {
        requestPosts(Post.ACTIVE);
        requestPosts(Post.INACTIVE);
    }

    private void requestPosts(final String active){
        new CurrentUserProfileRequest().request(active, new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e == null) {
                    profile.populateList(active, posts);
                    if(holder != null){
                        holder.populateListView(active, posts);
                    } else {
                        Activity activity = getActivity();
                        if (activity != null) {
                            ((NewVoActivity)getActivity()).attachDetachFragment();
                        }
                    }
                } else {
                    requestPosts(active);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        List<Post> activePosts = profile.activePosts;
        List<Post> inactivePosts = profile.inactivePosts;
        if(activePosts == null || inactivePosts == null){
            rootView = inflater.inflate(R.layout.text, container, false);
        } else if(activePosts.size() == 0 && inactivePosts.size() == 0){
            ((DrawerActivity)getActivity()).setActionBarLoading(false);
            rootView = inflater.inflate(R.layout.text, container, false);
            final TextView text = (TextView) rootView.findViewById(R.id.text);
            text.setText("There don't appear to be any posts here. Go create one...");
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() != null) {
                        ((NewVoActivity)getActivity()).displayView(getString(R.string.title_create_post));
                    }
                }
            });
        } else {
            ((DrawerActivity)getActivity()).setActionBarLoading(false);
            rootView = inflater.inflate(R.layout.profile, container, false);
            if (holder == null) {
                holder = new ProfileViewHolder(rootView, profile);
            } else {
                holder.setView(rootView);
            }

            return rootView;
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        if(holder != null){
            holder.onDestroyView();
        }
        super.onDestroyView();
    }

    @Override
    public boolean hasLoaded() {
        return profile != null && profile.loaded();
    }
}
