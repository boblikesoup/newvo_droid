package com.newvo.android.request;

/**
 * Created by David on 4/6/2014.
 */
public class ShowPostRequest extends AbstractRequest {

    public ShowPostRequest(){
        super("/api/v1/posts/", GET);
    }

    public void setPostId(int id){
        setUrlData(id + "");
    }
}
