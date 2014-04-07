package com.newvo.android.request;

/**
 * Created by David on 4/6/2014.
 */
public class CreateFollowingRequest extends AbstractRequest {

    public CreateFollowingRequest() {
        super("/api/v1/followings", POST);
    }

    public void setFollowedId(int id){
        addParam("followed_id", id + "");
    }
}

