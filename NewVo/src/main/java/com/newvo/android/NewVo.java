package com.newvo.android;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.Session;
import com.newvo.android.friends.FacebookPublisher;
import com.newvo.android.groups.GroupsFragment;
import com.personagraph.api.PGAgent;


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        if(FacebookPublisher.statusCallback != null){
            FacebookPublisher.statusCallback.call(Session.getActiveSession(), Session.getActiveSession().getState(), null);
        }

    }

    @Override
    public void displayFragment(Fragment fragment, String name, String tag, String parentTag) {
        super.displayFragment(fragment, name, tag, parentTag);
        if(tag == null){
            return;
        }
        if(compareToString(name,R.string.title_home)){
            PGAgent.logEvent("main view controller");
        } else if(compareToString(name,R.string.title_create_post)){
            PGAgent.logEvent("create post view controller");
        } else if(compareToString(name,R.string.title_profile)){
            PGAgent.logEvent("my posts view controller");
        } else if(compareToString(name,R.string.title_groups)){
            PGAgent.logEvent("main groups tab");
        }

    }

    @Override
    public void displayChildFragment(Fragment fragment, String name, String tag) {
        super.displayChildFragment(fragment, name, tag);
        if(tag.equals("Caption")){
            PGAgent.logEvent("caption zoomed");
        } else if(tag.equals("Image1") || tag.equals("Image2") || tag.equals("SummaryImage")){
            PGAgent.logEvent("image zoomed");
        } else if(tag.equals("AddSuggestion") || tag.equals("SuggestionsList")){
            PGAgent.logEvent("comment view controller");
        } else if(tag.equals("CreateGroup")){
            PGAgent.logEvent("create group controller");
        } else if(tag.equals("SingleGroup")){
            PGAgent.logEvent("single group controller");
        }else if(tag.equals("Tagging")){
            PGAgent.logEvent("entered tagging page");
        }
    }

    private boolean compareToString(String name, int resId){
        if(name == null){
            return false;
        } else {
            return name.equals(getString(resId));
        }
    }
}
