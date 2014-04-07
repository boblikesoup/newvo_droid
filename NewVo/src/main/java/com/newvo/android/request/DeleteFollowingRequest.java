package com.newvo.android.request;

/**
 * Created by David on 4/6/2014.
 */
public class DeleteFollowingRequest extends AbstractRequest {

    public DeleteFollowingRequest() {
        super("/api/v1/followings/:", DELETE);
    }

    public void setFollowedId(int id){
        setUrlData(id + "");
    }
}
