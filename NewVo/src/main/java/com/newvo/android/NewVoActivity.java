package com.newvo.android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.newvo.android.parse.User;
import com.newvo.android.slidingmenu.NavigationDrawerListAdapter;
import com.personagraph.api.PGAgent;

/**
 * Created by David on 4/11/2014.
 */
public abstract class NewVoActivity extends DrawerActivity {

    // nav drawer title
    private FragmentRetriever fragmentRetriever;

    private NavigationDrawerListAdapter adapter;

    public NewVoActivity(){
        super();

    }

    @Override
    protected void onStart() {
        super.onStart();
        PGAgent.startSession(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        PGAgent.endSession(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragmentRetriever = new FragmentRetriever(this);
        super.onCreate(savedInstanceState);

        PGAgent.shareFBToken(User.getCurrentUser().getFacebookId(), LoginView.PERMISSIONS);
        adapter = new NavigationDrawerListAdapter(getApplicationContext(),
                fragmentRetriever.getNavigationDrawerItems());

        drawerList = (ListView) findViewById(R.id.list_slidermenu);
        drawerList.setOnItemClickListener(new SlideMenuClickListener());
        drawerList.setAdapter(adapter);

        getFragmentManager().addOnBackStackChangedListener(backStackChangedListener);


        if (savedInstanceState == null) {
            // on first time display view for first nav item
            String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
            displayView(navMenuTitles[0]);
            setTitle(navMenuTitles[0]);
        }

    }

    protected void displayView(String name){
        displayView(name, null);
    }

    protected void displayView(String name, String tag) {
        // update the main content by replacing fragments
        Fragment fragment = retrieveFragment(name);

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
                NewVoActivity.this.tag = backStackEntryAt.getName();
                setTitle(backStackEntryAt.getBreadCrumbTitle());
            }
        }
    };

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
        Fragment fragment = retrieveFragment(name);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();

        FragmentTransaction transaction = fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment, tag).setBreadCrumbTitle(name).addToBackStack(tag);
        transaction.commit();
    }

    protected abstract Fragment retrieveFragment(String name);


    //endregion

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
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

    //region Toggle Support

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
