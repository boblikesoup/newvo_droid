package com.newvo.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.newvo.android.request.CurrentUserProfileRequest;

/**
 * Created by David on 4/13/2014.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.comparison, container, false);
        ButterKnife.inject(this, rootView);
        CurrentUserProfileRequest.loadSingle(getActivity(), new ComparisonViewHolder(rootView));
        return rootView;
    }

}
