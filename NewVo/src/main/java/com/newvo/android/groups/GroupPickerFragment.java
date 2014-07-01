package com.newvo.android.groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.newvo.android.R;
import com.newvo.android.parse.Group;
import com.newvo.android.util.ChildFragment;

/**
 * Created by David on 6/29/2014.
 */
public class GroupPickerFragment extends GroupsFragment implements ChildFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        newGroup.setVisibility(View.GONE);
        return view;
    }

    @Override
    protected ArrayAdapter<Group> getAdapter() {
        return new GroupPickerAdapter(getActivity(), R.layout.picker_item);
    }
}
