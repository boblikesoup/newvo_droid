package com.newvo.android;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.astuetz.PagerSlidingTabStrip;
import com.newvo.android.parse.Post;

import java.util.List;

/**
 * Created by David on 5/17/2014.
 */
public class ProfileViewHolder {

    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabs;

    @InjectView(R.id.active)
    ListView activeList;

    @InjectView(R.id.inactive)
    ListView inactiveList;

    @InjectView(R.id.pager)
    ViewPager pager;

    private Context context;


    public ProfileViewHolder(View view) {
        setView(view);
    }

    public void setView(View view) {
        ButterKnife.inject(this, view);
        context = view.getContext();

        activeList.setAdapter(getAdapter(context, Post.ACTIVE));
        inactiveList.setAdapter(getAdapter(context, Post.INACTIVE));

        tabs.setTextColor(context.getResources().getColor(android.R.color.white));
        tabs.setIndicatorColor(context.getResources().getColor(R.color.light_semi_transparent));
        tabs.setShouldExpand(true);
        tabs.setAllCaps(false);
        tabs.setTypeface(Typeface.SERIF, 0);
        tabs.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22, context.getResources().getDisplayMetrics()));

        tabs.setViewPager(pager);


        pager.setAdapter(pagerAdapter);
    }

    public void populateListView(ListView listView, List<Post> posts){
        if(listView != null && listView.getAdapter() != null && listView.getAdapter() instanceof ArrayAdapter) {
            ((ArrayAdapter) listView.getAdapter()).addAll(posts);
        }
    }

    private SummaryAdapter getAdapter(Context context, String active){
        final SummaryAdapter adapter = new SummaryAdapter(context, R.layout.summary, new SummaryAdapter.EditPostCallback() {
            @Override
            public void editPost(Post post) {
                changeLists(post);
            }
        });
        adapter.setActive(active);

        return adapter;
    }

    PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0){
                return context.getString(R.string.active);
            } else {
                return context.getString(R.string.inactive);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(position == 0){
                return activeList;
            } else {
                return inactiveList;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            if(view != null && object != null){
                return object.equals(view);
            }
            return false;
        }
    };

    public void setCurrentItem(int currentItem) {
        if(pager != null) {
            pager.setCurrentItem(currentItem);
        }
    }
}
