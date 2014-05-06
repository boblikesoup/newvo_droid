package com.newvo.android.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.newvo.android.DrawerActivity;
import com.newvo.android.R;

/**
 * Created by David on 5/5/2014.
 */
public class DrawerToggle implements View.OnClickListener, DrawerLayout.DrawerListener, FragmentManager.OnBackStackChangedListener {

    private boolean back;
    private DrawerLayout layout;
    private DrawerActivity activity;

    public DrawerToggle(DrawerActivity activity, DrawerLayout layout) {
        this.activity = activity;
        this.layout = layout;
        setBackButton(false);
    }

    @Override
    public void onClick(View v) {
        //Always close the drawer if it's open.
        if (layout.isDrawerVisible(GravityCompat.START)) {
            layout.closeDrawer(GravityCompat.START);
        } else {
            //If the drawer is closed and back is true, go back instead of opening drawer.
            if (back) {
                activity.onBackPressed();
            } else {
                layout.openDrawer(GravityCompat.START);
            }
        }
    }

    public void setBackButton(boolean back) {
        this.back = back;
        if(back){
            setNavigationIcon(R.drawable.ic_action_previous_item);
        } else {
            setNavigationIcon(R.drawable.ic_drawer);
        }
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {
        (activity.getActionBar().getCustomView().findViewById(R.id.navigation_button)).startAnimation(new TranslateAnimation(0, 1.0f, 0, 0));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void onConfigurationChanged(Configuration newConfig) {

    }

    public void onPostCreate(Bundle savedInstanceState) {

    }

    private void setNavigationIcon(int resId) {
        ((ImageView)activity.getActionBar().getCustomView().findViewById(R.id.navigation_button)).setImageResource(resId);
    }

    @Override
    public void onBackStackChanged() {
        Fragment activeFragment = activity.getActiveFragment();
        setBackButton(activeFragment != null && activeFragment instanceof ChildFragment);
    }

}
