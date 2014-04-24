package com.newvo.android.request;

import android.content.Context;

/**
 * Created by David on 4/6/2014.
 */
public class DeleteFollowingRequest extends AbstractRequest {

    public DeleteFollowingRequest(Context context, int id) {
        super(context, "/api/v1/followings/", DELETE);
        setFollowedId(id);
    }

    private void setFollowedId(int id) {
        setUrlData(id + "");
    }
}
