package com.newvo.android;

import android.app.Fragment;
import android.os.Bundle;
import com.newvo.android.groups.GroupsFragment;


public class NewVo extends NewVoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void displayView(String name) {
        setTheme(R.style.CustomActionBarTheme);
        super.displayView(name);
    }


    @Override
    protected Fragment retrieveFragment(String name) {
        if(compareToString(name,R.string.title_home)){
            return new HomeFragment();
        } else if(compareToString(name,R.string.title_create_post)){
            return new CreatePostFragment();
        } else if(compareToString(name,R.string.title_profile)){
            return new ProfileFragment();
        } else if(compareToString(name,R.string.title_groups)){
            return new GroupsFragment();
        } else if(compareToString(name,R.string.action_settings)){
            return new SettingsFragment();
        }
        //Default to home.
        return new HomeFragment();
    }

    private boolean compareToString(String name, int resId){
        if(name == null){
            return false;
        } else {
            return name.equals(getString(resId));
        }
    }
}
