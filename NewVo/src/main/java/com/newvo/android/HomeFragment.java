package com.newvo.android;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by David on 4/13/2014.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ListView listView = new ListView(inflater.getContext());

        listView.setAdapter(new ComparisonAdapter(inflater.getContext(), R.layout.comparison));
        return listView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
