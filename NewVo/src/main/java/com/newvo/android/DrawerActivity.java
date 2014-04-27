package com.newvo.android;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.newvo.android.slidingmenu.NavigationDrawerListAdapter;

/**
 * Created by David on 4/11/2014.
 */
public class DrawerActivity extends Activity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    // nav drawer title
    private FragmentRetriever fragmentRetriever;

    private ListView drawerList;
    private NavigationDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        fragmentRetriever = new FragmentRetriever(this);

        drawerList = (ListView) findViewById(R.id.list_slidermenu);
        drawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        adapter = new NavigationDrawerListAdapter(getApplicationContext(),
                fragmentRetriever.getNavigationDrawerItems());
        drawerList.setAdapter(adapter);


        //Set up the drawer toggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            @Override
            public void onDrawerClosed(View view) {

                getActionBar().setTitle(getTitle());
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(getTitle());
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
            displayView(navMenuTitles[0]);
        }


    }

    protected void displayView(String name) {
        // update the main content by replacing fragments
        Fragment fragment = fragmentRetriever.retrieveFragment(name);

        if (fragment != null) {
            displayFragment(fragment, name);

            // update selected item and title, then close the drawer
            drawerLayout.closeDrawer(drawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    private FragmentManager.OnBackStackChangedListener backStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            int backStackEntryCount = getFragmentManager().getBackStackEntryCount();
            if (backStackEntryCount > 0) {
                FragmentManager.BackStackEntry backStackEntryAt = getFragmentManager().getBackStackEntryAt(backStackEntryCount - 1);
                setTitle(backStackEntryAt.getBreadCrumbTitle());
            } else {
                onBackPressed();
            }
        }
    };

    public void displayFragment(Fragment fragment, String name){
        FragmentManager fragmentManager = getFragmentManager();
        if(name.equals(getString(R.string.title_home))){
            fragmentManager.removeOnBackStackChangedListener(backStackChangedListener);
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.addOnBackStackChangedListener(backStackChangedListener);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment, name).setBreadCrumbTitle(name).addToBackStack(name);
        transaction.commit();

    }

    public void refreshFragment(){
        Fragment currentFragment = getFragmentManager().findFragmentByTag(getTitle().toString());
        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
        fragTransaction.detach(currentFragment);
        fragTransaction.attach(currentFragment);
        fragTransaction.commit();
    }


    //region Handling Action Bar Interaction
    //Initializes menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_vo, menu);
        return true;
    }

    //Goes off whenever a menu item is selected on the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
    //endregion

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        getActionBar().setTitle(title);
        int resId = fragmentRetriever.retrieveIcon(title.toString());
        if(resId > -1){
            getActionBar().setIcon(resId);
        }
        int position = fragmentRetriever.retrievePosition(title.toString());
        if(position > -1){
            drawerList.setItemChecked(position, true);
            drawerList.setSelection(position);
        }
    }

    //region Toggle Support
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }
    //endregion

    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(fragmentRetriever.retrieveName(position));
        }
    }
}
