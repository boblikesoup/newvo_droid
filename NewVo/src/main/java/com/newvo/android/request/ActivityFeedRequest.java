package com.newvo.android.request;

/**
 * Created by David on 4/6/2014.
 */
public class ActivityFeedRequest extends AbstractRequest {

    public ActivityFeedRequest() {
        super("/api/v1/activity_feed", GET);
    }
}
