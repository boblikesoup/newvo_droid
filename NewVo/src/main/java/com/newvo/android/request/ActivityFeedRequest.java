package com.newvo.android.request;

import android.content.Context;

/**
 * Created by David on 4/6/2014.
 */
public class ActivityFeedRequest extends AbstractRequest {

    public ActivityFeedRequest(Context context) {
        super(context, "/api/v1/activity_feed", GET);
    }
}
