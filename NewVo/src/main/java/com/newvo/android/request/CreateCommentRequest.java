package com.newvo.android.request;

/**
 * Created by David on 4/5/2014.
 */
public class CreateCommentRequest extends PostRequest {

    private static final String BODY = "body";

    public CreateCommentRequest() {
        super("/api/v1/posts/:");
    }

    public void setPostId(int id){
        setUrlData(id + "/comments");
    }

    public void setBody(String body){
        addBodyParam(BODY, body);
    }
}
