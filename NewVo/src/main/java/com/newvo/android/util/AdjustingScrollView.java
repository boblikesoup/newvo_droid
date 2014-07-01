package com.newvo.android.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by David on 6/30/2014.
 */
public class AdjustingScrollView extends ScrollView {

    public AdjustingScrollView(Context context) {
        super(context);
    }

    public AdjustingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustingScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private int onLayoutScrollByX = 0;
    private int onLayoutScrollByY = 0;

    public void planScrollBy(int x, int y) {
        onLayoutScrollByX += x;
        onLayoutScrollByY += y;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        doPlannedScroll();
    }

    public void doPlannedScroll() {
        if (onLayoutScrollByX != 0 || onLayoutScrollByY != 0) {
            scrollBy(onLayoutScrollByX, onLayoutScrollByY);
            onLayoutScrollByX = 0;
            onLayoutScrollByY = 0;
        }
    }
}
