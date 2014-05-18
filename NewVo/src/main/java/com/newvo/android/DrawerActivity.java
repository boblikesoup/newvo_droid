package com.newvo.android;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.newvo.android.slidingmenu.NavigationDrawerListAdapter;
import com.newvo.android.util.DrawerToggle;
import com.newvo.android.util.LoadingFragment;

/**
 * Created by David on 4/11/2014.
 */
public class DrawerActivity extends Activity {

    private DrawerLayout drawerLayout;

    private DrawerToggle drawerToggle;

    // nav drawer title
    private FragmentRetriever fragmentRetriever;

    private ListView drawerList;
    private NavigationDrawerListAdapter adapter;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        fragmentRetriever = new FragmentRetriever(this);

        drawerList = (ListView) findViewById(R.id.list_slidermenu);
        drawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        adapter = new NavigationDrawerListAdapter(getApplicationContext(),
                fragmentRetriever.getNavigationDrawerItems());
        drawerList.setAdapter(adapter);

        getActionBar().setCustomView(R.layout.action_bar);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);

        drawerToggle = new DrawerToggle(this, drawerLayout);
        getActionBar().getCustomView().setOnClickListener(drawerToggle);
        drawerLayout.setDrawerListener(drawerToggle);

        getFragmentManager().addOnBackStackChangedListener(backStackChangedListener);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
            displayView(navMenuTitles[0]);
            setTitle(navMenuTitles[0]);
        }

        getFragmentManager().addOnBackStackChangedListener(drawerToggle);

    }

    protected void displayView(String name){
        displayView(name, null);
    }

    protected void displayView(String name, String tag) {
        // update the main content by replacing fragments
        Fragment fragment = fragmentRetriever.retrieveFragment(name);

        if (fragment != null) {
            displayFragment(fragment, name, tag, null);

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
                DrawerActivity.this.tag = backStackEntryAt.getName();
                setTitle(backStackEntryAt.getBreadCrumbTitle());
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int backStackEntryCount = getFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            super.onBackPressed();
        }

    }

    public void displayFragment(Fragment fragment, String name, String tag, String parentTag) {
        FragmentManager fragmentManager = getFragmentManager();
        if(tag == null) {
            tag = name;
        }
        if (tag.equals(getString(R.string.title_home))) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        if (tag.equals(this.tag)) {
            restartFragment();
        } else {
            //Reset the back stack to only home if there is no parent tag.
            fragmentManager.popBackStack((parentTag != null) ? parentTag : getString(R.string.title_home), 0);
            FragmentTransaction transaction = fragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                    .replace(R.id.frame_container, fragment, tag).setBreadCrumbTitle(name).addToBackStack(tag);
            transaction.commit();
        }
    }

    //Finds a parent tag to keep parent on back stack.
    public void displayChildFragment(Fragment fragment, String name, String tag) {
        FragmentManager fragmentManager = getFragmentManager();
        String parentName = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();

        displayFragment(fragment, name, tag, parentName);
    }

    public void attachDetachFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if(fragment != null) {
            fragmentManager.beginTransaction().detach(fragment).attach(fragment).commit();
        }
    }

    public void restartFragment(){
        String name = getTitle().toString();
        Fragment fragment = fragmentRetriever.retrieveFragment(name);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();

        FragmentTransaction transaction = fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment, tag).setBreadCrumbTitle(name).addToBackStack(tag);
        transaction.commit();
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
    //endregion

    @Override
    public void setTitle(CharSequence title) {
        Fragment activeFragment = getActiveFragment();
        setActionBarLoading(activeFragment != null && activeFragment instanceof LoadingFragment);
        super.setTitle(title);
        setActionBarTitle(title);
        int position = fragmentRetriever.retrievePosition(tag);
        if (position > -1) {
            //Icon and position depend on top-level.
            int resId = fragmentRetriever.retrieveIcon(title.toString());
            if (resId > -1) {
                setActionBarIcon(resId);
            }
            drawerList.setItemChecked(position, true);
            drawerList.setSelection(position);
        }
    }

    private void setActionBarIcon(int resId) {
        ((ImageView)getActionBar().getCustomView().findViewById(R.id.icon)).setImageResource(resId);
    }

    public void setActionBarLoading(boolean loading){
        getActionBar().getCustomView().findViewById(R.id.progress_bar).setVisibility(loading ? View.VISIBLE : View.GONE);
        getActionBar().getCustomView().findViewById(R.id.icon).setVisibility(loading ? View.GONE : View.VISIBLE);
    }

    private void setActionBarTitle(CharSequence title) {
        ((TextView)getActionBar().getCustomView().findViewById(R.id.title)).setText(title);
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
        drawerToggle.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
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

    public Fragment getActiveFragment() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1).getName();
        return getFragmentManager().findFragmentByTag(tag);
    }
}
