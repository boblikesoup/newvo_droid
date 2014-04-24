package com.newvo.android.request;

import android.content.Context;

/**
 * Created by David on 4/6/2014.
 */
public class CreateFollowingRequest extends PostRequest {

    public CreateFollowingRequest(Context context, int id) {
        super(context, "/api/v1/followings");
        setFollowedId(id);
    }

    private void setFollowedId(int id) {
        addBodyParam("followed_id", id + "");
    }
}

