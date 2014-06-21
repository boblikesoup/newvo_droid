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

    private final Profile profile;

    private SummaryAdapter activeAdapter;
    private SummaryAdapter inactiveAdapter;

    private Context context;


    public ProfileViewHolder(View view, Profile profile) {
        this.profile = profile;
        setView(view);
        if (profile.activePosts != null) {
            populateListView(Post.ACTIVE, profile.activePosts);
        }
        if (profile.inactivePosts != null) {
            populateListView(Post.INACTIVE, profile.inactivePosts);
        }
    }

    public void setView(View view) {
        ButterKnife.inject(this, view);
        context = view.getContext();

        if (activeAdapter == null) {
            activeAdapter = generateAdapter(context);
        }
        if (inactiveAdapter == null) {
            inactiveAdapter = generateAdapter(context);
        }

        activeList.setAdapter(activeAdapter);
        inactiveList.setAdapter(inactiveAdapter);

        pager.setAdapter(pagerAdapter);

        tabs.setTextColor(context.getResources().getColor(android.R.color.white));
        tabs.setIndicatorColor(context.getResources().getColor(R.color.light_semi_transparent));
        tabs.setShouldExpand(true);
        tabs.setAllCaps(false);
        tabs.setTypeface(Typeface.SERIF, 0);
        tabs.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22, context.getResources().getDisplayMetrics()));

        tabs.setViewPager(pager);


        //When tabbing back, select the tab the post was on.
        if (profile.selectedPost != null) {
            if (profile.inactivePosts.contains(profile.selectedPost)) {
                setCurrentItem(1);
            }
        }
        profile.selectedPost = null;
    }



    public void populateListView(String type, List<Post> posts) {
        ListView listView = type.equals(Post.ACTIVE) ? activeList : inactiveList;
        if (listView != null && listView.getAdapter() != null && listView.getAdapter() instanceof ArrayAdapter) {
            ((ArrayAdapter) listView.getAdapter()).addAll(posts);
        }
    }

    private SummaryAdapter generateAdapter(final Context context) {
        final SummaryAdapter adapter = new SummaryAdapter(context, R.layout.summary);
        adapter.setSaveCallback(new EditPostCallback() {
            @Override
            public void editPost(Post post) {
                changeLists(post);
                if (context != null) {
                    ((NewVoActivity) context).attachDetachFragment();
                }
            }
        });
        adapter.setOpenSuggestionsCallback(new EditPostCallback() {
            @Override
            public void editPost(Post post) {
                if(profile != null){
                    profile.selectedPost = post;
                }
            }
        });
        return adapter;
    }

    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return context.getString(R.string.active);
            } else {
                return context.getString(R.string.inactive);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == 0) {
                return activeList;
            } else {
                return inactiveList;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            if (view != null && object != null) {
                return object.equals(view);
            }
            return false;
        }
    };

    public void setCurrentItem(int currentItem) {
        if (pager != null) {
            pager.setCurrentItem(currentItem);
        }
    }

    public void changeLists(Post post) {
        if (post != null) {
            if (post.getStatus().equals(Post.INACTIVE)) {
                if (inactiveList != null && activeList != null) {
                    ((ArrayAdapter) activeList.getAdapter()).remove(post);
                    ((ArrayAdapter) inactiveList.getAdapter()).add(post);
                }
            } else if (post.getStatus().equals(Post.ACTIVE)) {
                if (inactiveList != null && activeList != null) {
                    ((ArrayAdapter) inactiveList.getAdapter()).remove(post);
                    ((ArrayAdapter) activeList.getAdapter()).add(post);
                }
            }
        }
    }

    public void onDestroyView() {
        activeList.setAdapter(null);
        inactiveList.setAdapter(null);
        activeList = null;
        inactiveList = null;
        tabs = null;
        pager = null;
    }
}
