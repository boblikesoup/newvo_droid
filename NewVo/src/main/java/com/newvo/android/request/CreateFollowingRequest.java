package com.newvo.android.request;

/**
 * Created by David on 4/6/2014.
 */
public class CreateFollowingRequest extends PostRequest {

    public CreateFollowingRequest() {
        super("/api/v1/followings");
    }

    public void setFollowedId(int id){
        addBodyParam("followed_id", id + "");
    }
}

