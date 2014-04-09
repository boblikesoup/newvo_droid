package com.newvo.android.request;

/**
 * Created by David on 4/5/2014.
 */
public class CreateCommentRequest extends PostRequest {

    private static final String BODY = "body";

    public CreateCommentRequest(int id, String body) {
        super("/api/v1/posts/:");
        setPostId(id);
        setBody(body);
    }

    private void setPostId(int id){
        setUrlData(id + "/comments");
    }

    private void setBody(String body){
        addBodyParam(BODY, body);
    }
}
