package com.newvo.android;

import android.content.Context;
import android.content.res.TypedArray;
import com.newvo.android.slidingmenu.NavigationDrawerItem;

import java.util.ArrayList;

/**
 * Created by David on 4/12/2014.
 */
public class FragmentRetriever {

    private ArrayList<NavigationDrawerItem> navDrawerItems;

    public FragmentRetriever(Context context){
        navDrawerItems = new ArrayList<NavigationDrawerItem>();

        // load slide menu items
        String[] navMenuTitles = context.getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        TypedArray navMenuIcons = context.getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        // adding nav drawer items to array
        for(int i = 0; i < navMenuTitles.length; i++){
            add(new NavigationDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));

            navMenuIcons.recycle();
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
