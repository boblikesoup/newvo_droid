package com.newvo.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import com.newvo.android.R;

/**
 * Created by David on 5/5/2014.
 */
public class DrawerToggle implements View.OnClickListener, DrawerLayout.DrawerListener {

    private boolean back;
    private DrawerLayout layout;
    private Activity activity;

    private static final float TRANSLATE_MIN = 0.0f;
    private static final float TRANSLATE_MAX = -32.0f;

    public DrawerToggle(Activity activity, DrawerLayout layout) {
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
                //Close any open keyboards before opening the drawer.
                InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if(inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),0)){
                    layout.requestFocus();
                }
                layout.openDrawer(GravityCompat.START);
            }
        }
    }

    public void setBackButton(boolean back) {
        this.back = back;
        if (back) {
            setNavigationIcon(R.drawable.back_arrow);
        } else {
            setNavigationIcon(R.drawable.drawer);
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
        if (newState == DrawerLayout.STATE_SETTLING) {
            animateDrawerState();
        }
    }

    public void animateDrawerState() {
        TranslateAnimation animation = new TranslateAnimation(TRANSLATE_MIN, TRANSLATE_MAX, 0.0f, 0.0f);
        animation.setDuration(300);
        animation.setFillAfter(true);
        if (layout.isDrawerVisible(GravityCompat.START)) {
            animation.setInterpolator(new ReverseInterpolator());
        }
        (activity.getActionBar().getCustomView().findViewById(R.id.navigation_button)).startAnimation(animation);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void onConfigurationChanged(Configuration newConfig) {

    }

    public void onPostCreate(Bundle savedInstanceState) {

    }

    private void setNavigationIcon(int resId) {
        ((ImageView) activity.getActionBar().getCustomView().findViewById(R.id.navigation_button)).setImageResource(resId);
    }


}
