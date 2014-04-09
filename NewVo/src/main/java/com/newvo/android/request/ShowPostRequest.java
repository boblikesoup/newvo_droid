package com.newvo.android.request;

/**
 * Created by David on 4/6/2014.
 */
public class ShowPostRequest extends AbstractRequest {

    public ShowPostRequest(int id){
        super("/api/v1/posts/", GET);
        setPostId(id);
    }

    private void setPostId(int id){
        setUrlData(id + "");
    }
}
