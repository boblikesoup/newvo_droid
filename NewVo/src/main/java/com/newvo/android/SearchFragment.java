package com.newvo.android;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import butterknife.InjectView;

/**
 * Created by David on 4/11/2014.
 */
public class SearchFragment extends ListFragment {

    @InjectView(R.id.search)
    EditText search;

    @InjectView(R.id.search_list)
    ListView searchList;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search, container, false);

        searchList.setAdapter(new ComparisonAdapter(NewVo.getContext(), R.layout.comparison));

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setTitle(R.string.title_search);
    }
}
