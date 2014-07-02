package com.newvo.android.groups;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.DrawerActivity;
import com.newvo.android.NewVoActivity;
import com.newvo.android.R;
import com.newvo.android.parse.Group;
import com.newvo.android.remote.UserGroupsRequest;
import com.newvo.android.util.LoadingFragment;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

/**
 * Created by David on 6/22/2014.
 */
public class GroupsFragment extends Fragment implements LoadingFragment {

    @InjectView(R.id.new_group)
    LinearLayout newGroup;

    @InjectView(R.id.groups_list)
    ListView groupsList;

    List<Group> groups;

    public GroupsFragment(){

    }

    public void requestGroups() {
        Activity activity = getActivity();
        if(activity != null) {
            groups = null;
            ((DrawerActivity) activity).setActionBarLoading(true);
        }
        new UserGroupsRequest().request(new FindCallback<Group>() {
            @Override
            public void done(List<Group> groups, ParseException e) {
                if(e == null) {
                    GroupsFragment.this.groups = groups;
                    if (groups != null && groups.size() > 0 && groupsList != null && groupsList.getAdapter() != null && groupsList.getAdapter() instanceof ArrayAdapter) {
                        ((ArrayAdapter) groupsList.getAdapter()).addAll(groups);
                    }
                    Activity activity = getActivity();
                    if(activity != null) {
                        ((DrawerActivity) activity).setActionBarLoading(false);
                    }
                } else {
                    requestGroups();
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.groups, container, false);
        ButterKnife.inject(this, rootView);

        groupsList.setAdapter(getAdapter());

        newGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NewVoActivity) getActivity()).displayChildFragment(new EditGroupFragment(null), getActivity().getString(R.string.title_groups), "CreateGroup");
            }
        });

        requestGroups();

        return rootView;
    }

    protected ArrayAdapter<Group> getAdapter() {
        return new GroupAdapter(getActivity(), R.layout.group_item);
    }

    @Override
    public boolean hasLoaded() {
        return groups != null;
    }
}
