package com.newvo.android.request;

/**
 * Created by David on 4/6/2014.
 */
public class DeletePostRequest extends AbstractRequest {

    public DeletePostRequest(int id){
        super("/api/v1/posts/", DELETE);
        setPostId(id);
    }

    private void setPostId(int id){
        setUrlData(id + "");
    }
}
