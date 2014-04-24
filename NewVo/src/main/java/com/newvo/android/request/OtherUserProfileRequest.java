package com.newvo.android.request;

import android.content.Context;

/**
 * Created by David on 4/6/2014.
 */
public class OtherUserProfileRequest extends AbstractRequest {

    public OtherUserProfileRequest(Context context, int id) {
        super(context, "api/v1/users/:id", GET);
        setId(id);
    }

    private void setId(int id) {
        setUrlData(id + "");
    }
}
