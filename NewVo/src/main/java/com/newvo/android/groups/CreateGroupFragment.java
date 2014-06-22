package com.newvo.android.groups;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.newvo.android.R;
import com.newvo.android.util.ChildFragment;

/**
 * Created by David on 6/22/2014.
 */
public class CreateGroupFragment extends Fragment implements ChildFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.create_group, container, false);
        ButterKnife.inject(this, rootView);


        return rootView;
    }
}
