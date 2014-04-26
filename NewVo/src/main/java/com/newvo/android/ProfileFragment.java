package com.newvo.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.newvo.android.parse.Post;
import com.newvo.android.remote.CurrentUserProfileRequest;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

/**
 * Created by David on 4/20/2014.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ListView listView = new ListView(inflater.getContext());

        final SummaryAdapter adapter = new SummaryAdapter(inflater.getContext(), R.layout.summary);
        listView.setAdapter(adapter);

        new CurrentUserProfileRequest().request(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                adapter.addAll(posts);
            }
        });
        return listView;
    }
}
