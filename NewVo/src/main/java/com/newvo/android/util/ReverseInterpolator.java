package com.newvo.android.util;

import android.view.animation.Interpolator;

/**
 * Created by David on 5/6/2014.
 */
public class ReverseInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float paramFloat) {
        return Math.abs(paramFloat -1f);
    }
}