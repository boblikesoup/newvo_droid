package com.newvo.android.request;

import android.content.Context;

/**
 * Created by David on 4/6/2014.
 */
public class ShowPostRequest extends AbstractRequest {

    public ShowPostRequest(Context context, int id) {
        super(context, "/api/v1/posts/", GET);
        setPostId(id);
    }

    private void setPostId(int id) {
        setUrlData(id + "");
    }
}
