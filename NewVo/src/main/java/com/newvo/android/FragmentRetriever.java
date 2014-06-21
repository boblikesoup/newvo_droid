package com.newvo.android;

import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import com.newvo.android.slidingmenu.NavigationDrawerItem;

import java.util.ArrayList;

/**
 * Created by David on 4/12/2014.
 */
public class FragmentRetriever {

    private ArrayList<NavigationDrawerItem> navDrawerItems;
    private Context context;

    public FragmentRetriever(Context context){
        this.context = context;
        navDrawerItems = new ArrayList<NavigationDrawerItem>();

        // load slide menu items
        String[] navMenuTitles = this.context.getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        TypedArray navMenuIcons = this.context.getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        // adding nav drawer items to array
        for(int i = 0; i < navMenuTitles.length; i++){
            add(new NavigationDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));

            navMenuIcons.recycle();
        }
    }


    public Fragment retrieveFragment(String name) {
        if(compareToString(name,R.string.title_home)){
            return new HomeFragment();
        } else if(compareToString(name,R.string.title_create_post)){
            return new CreatePostFragment();
        } else if(compareToString(name,R.string.title_profile)){
            return new ProfileFragment();
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
            return name.equals(context.getString(resId));
        }

    }

    public void add(NavigationDrawerItem navigationDrawerItem) {
        navDrawerItems.add(navigationDrawerItem);
    }

    public ArrayList<NavigationDrawerItem> getNavigationDrawerItems() {
        return navDrawerItems;
    }

    public int retrievePosition(String name) {
        for(int i = 0; i < navDrawerItems.size(); i++){
            if(navDrawerItems.get(i).getTitle().equals(name)){
                return i;
            }
        }
        return -1;
    }

    public int retrieveIcon(String name){
        for(int i = 0; i < navDrawerItems.size(); i++){
            if(navDrawerItems.get(i).getTitle().equals(name)){
                return navDrawerItems.get(i).getIcon();
            }
        }
        return -1;
    }

    public String retrieveName(int position) {
        return navDrawerItems.get(position).getTitle();
    }
}
