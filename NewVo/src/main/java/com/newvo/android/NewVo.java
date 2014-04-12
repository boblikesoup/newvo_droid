package com.newvo.android;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.newvo.android.util.TabAdapter;


public class NewVo extends AbstractNewVo {

    private static NewVo CONTEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NewVo.CONTEXT = this;

        addTab(R.drawable.search, new TabAdapter() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                getFragmentManager().beginTransaction().add(new SearchFragment(), "Search");
            }
        });
        addTab(R.drawable.profile, null);
        addTab(R.drawable.camera, null);
        addTab(R.drawable.messages, null);
        addTab(R.drawable.groups, null);

    }

    public static Context getContext(){
        return CONTEXT;
    }

    public static View inflate(int resource, ViewGroup root){
        return CONTEXT.getLayoutInflater().inflate(resource, root);
    }

}
