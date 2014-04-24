package com.newvo.android.request;

import android.content.Context;

/**
 * Created by David on 4/5/2014.
 */
public class CreateCommentRequest extends PostRequest {

    private static final String BODY = "body";

    public CreateCommentRequest(Context context, int id, String body) {
        super(context, "/api/v1/posts/:");
        setPostId(id);
        setBody(body);
    }

    private void setPostId(int id) {
        setUrlData(id + "/comments");
    }

    private void setBody(String body) {
        addBodyParam(BODY, body);
    }
}
