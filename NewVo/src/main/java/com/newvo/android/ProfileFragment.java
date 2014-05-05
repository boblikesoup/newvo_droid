package com.newvo.android;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.astuetz.PagerSlidingTabStrip;
import com.newvo.android.parse.Post;
import com.newvo.android.remote.CurrentUserProfileRequest;
import com.newvo.android.util.ZoomOutPageTransformer;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

/**
 * Created by David on 4/20/2014.
 */
public class ProfileFragment extends Fragment {

    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabs;

    @InjectView(R.id.active)
    ListView activeList;

    @InjectView(R.id.inactive)
    ListView inactiveList;

    @InjectView(R.id.pager)
    ViewPager pager;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile, container, false);
        ButterKnife.inject(this, rootView);

        activeList.setAdapter(getAdapter(getActivity(), Post.ACTIVE));
        inactiveList.setAdapter(getAdapter(getActivity(), Post.INACTIVE));

        pager.setPageTransformer(true, new ZoomOutPageTransformer());
        pager.setAdapter(pagerAdapter);

        tabs.setTextColor(getResources().getColor(android.R.color.white));
        tabs.setIndicatorColor(getResources().getColor(android.R.color.white));
        tabs.setShouldExpand(true);
        tabs.setAllCaps(false);
        tabs.setTypeface(Typeface.SERIF, 0);
        tabs.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22, getResources().getDisplayMetrics()));

        tabs.setViewPager(pager);

        return rootView;
    }

    private SummaryAdapter getAdapter(Context context, String active){
        final SummaryAdapter adapter = new SummaryAdapter(context, R.layout.summary);
        activeList.setAdapter(adapter);
        adapter.setActive(active);

        new CurrentUserProfileRequest().request(active, new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                adapter.addAll(posts);
            }
        });
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
                return getString(R.string.active);
            } else {
                return getString(R.string.inactive);
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
}
