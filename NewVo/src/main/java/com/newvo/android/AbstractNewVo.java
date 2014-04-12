package com.newvo.android;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import com.newvo.android.util.TabAdapter;

/**
 * Created by David on 4/11/2014.
 */
public class AbstractNewVo extends FragmentActivity {

    private ViewPager viewPager;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewPager = new ViewPager(this);
        setContentView(viewPager);

        actionBar = restoreActionBar();


    }


    protected void addTab(int drawable, TabAdapter listener){
        if(listener != null){
            actionBar.addTab(actionBar.newTab().setIcon(drawable).setTabListener(listener));
        } else {
            actionBar.addTab(actionBar.newTab().setIcon(drawable).setTabListener(new TabAdapter() {
                @Override
                public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                    setContentView(R.layout.fragment_main);
                }
            }));
        }
    }

    public ActionBar restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getTitle());
        return actionBar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
