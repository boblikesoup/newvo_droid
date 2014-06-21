package com.newvo.android;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.newvo.android.util.ChildFragment;
import com.newvo.android.util.DrawerToggle;
import com.newvo.android.util.LoadingFragment;

/**
 * Created by David on 6/21/2014.
 */
public class DrawerActivity extends Activity {
    protected DrawerLayout drawerLayout;
    protected DrawerToggle drawerToggle;
    protected ListView drawerList;
    protected String tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        drawerList = (ListView) findViewById(R.id.list_slidermenu);


        // setting the nav drawer list adapter
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        getActionBar().setCustomView(R.layout.action_bar);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);

        drawerToggle = new DrawerToggle(this, drawerLayout);
        getActionBar().getCustomView().setOnClickListener(drawerToggle);
        drawerLayout.setDrawerListener(drawerToggle);

        FragmentManager.OnBackStackChangedListener backStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment activeFragment = getActiveFragment();
                drawerToggle.setBackButton(activeFragment != null && activeFragment instanceof ChildFragment);
            }
        };
        getFragmentManager().addOnBackStackChangedListener(backStackChangedListener);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int backStackEntryCount = getFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            super.onBackPressed();
        }

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

    /**
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    protected void setActionBarIcon(int resId) {
        ((ImageView)getActionBar().getCustomView().findViewById(R.id.icon)).setImageResource(resId);
    }

    public void setActionBarLoading(boolean loading){
        getActionBar().getCustomView().findViewById(R.id.progress_bar).setVisibility(loading ? View.VISIBLE : View.GONE);
        getActionBar().getCustomView().findViewById(R.id.icon).setVisibility(loading ? View.GONE : View.VISIBLE);
    }

    protected void setActionBarTitle(CharSequence title) {
        ((TextView)getActionBar().getCustomView().findViewById(R.id.title)).setText(title);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public Fragment getActiveFragment() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1).getName();
        return getFragmentManager().findFragmentByTag(tag);
    }

    @Override
    public void setTitle(CharSequence title) {
        Fragment activeFragment = getActiveFragment();
        setActionBarLoading(activeFragment != null && activeFragment instanceof LoadingFragment && !((LoadingFragment) activeFragment).hasLoaded());
        super.setTitle(title);
        setActionBarTitle(title);
    }
}
