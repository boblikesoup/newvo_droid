package com.newvo.android.request;

/**
 * Created by David on 4/5/2014.
 */
public class CreateCommentRequest extends AbstractRequest {

    private static final String BODY = "body";

    public CreateCommentRequest() {
        super("/api/v1/posts/:", POST);
    }

    public void setPostId(int id){
        setUrlData(id + "/comments");
    }

    public void setBody(String body){
        addParam(BODY, body);
    }
}
