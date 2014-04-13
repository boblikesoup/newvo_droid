package com.newvo.android;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.TypedArray;
import com.newvo.android.slidingmenu.NavigationDrawerItem;

import java.util.ArrayList;

/**
 * Created by David on 4/12/2014.
 */
public class FragmentRetriever {

    private ArrayList<NavigationDrawerItem> navDrawerItems;
    private Activity activity;

    public FragmentRetriever(Activity activity){
        this.activity = activity;
        navDrawerItems = new ArrayList<NavigationDrawerItem>();

        // load slide menu items
        String[] navMenuTitles = activity.getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        TypedArray navMenuIcons = activity.getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        // adding nav drawer items to array
        for(int i = 0; i < navMenuTitles.length; i++){
            add(new NavigationDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));

            navMenuIcons.recycle();
        }
    }


    public Fragment retrieveFragment(String name) {
        if(name.equals(activity.getString(R.string.title_home))){
            return new HomeFragment();
        }
        //Default to home.
        return new SettingsFragment();
    }

    public void add(NavigationDrawerItem navigationDrawerItem) {
        navDrawerItems.add(navigationDrawerItem);
    }

    public ArrayList<NavigationDrawerItem> getNavigationDrawerItems() {
        return navDrawerItems;
    }

    public int retrievePosition(String name) {
        for(int i = 0; i < navDrawerItems.size(); i++){
            if(navDrawerItems.get(i).getTitle() == name){
                return i;
            }
        }
        return -1;
    }

    public String retrieveName(int position) {
        return navDrawerItems.get(position).getTitle();
    }
}
