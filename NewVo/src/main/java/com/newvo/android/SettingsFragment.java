package com.newvo.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;

/**
 * Created by David on 4/11/2014.
 */
public class SettingsFragment extends Fragment {

    @InjectView(R.layout.fragment_main)
    View main;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return main;
    }

}
