package com.newvo.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

        listView.setAdapter(new SummaryAdapter(inflater.getContext(), R.layout.summary));
        return listView;
    }
}
