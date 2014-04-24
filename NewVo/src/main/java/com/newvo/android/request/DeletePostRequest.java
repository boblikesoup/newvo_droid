package com.newvo.android.request;

import android.content.Context;

/**
 * Created by David on 4/6/2014.
 */
public class DeletePostRequest extends AbstractRequest {

    public DeletePostRequest(Context context, int id) {
        super(context, "/api/v1/posts/", DELETE);
        setPostId(id);
    }

    private void setPostId(int id) {
        setUrlData(id + "");
    }
}
