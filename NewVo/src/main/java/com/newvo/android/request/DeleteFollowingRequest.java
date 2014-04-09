package com.newvo.android.request;

/**
 * Created by David on 4/6/2014.
 */
public class DeleteFollowingRequest extends AbstractRequest {

    public DeleteFollowingRequest(int id) {
        super("/api/v1/followings/", DELETE);
        setFollowedId(id);
    }

    private void setFollowedId(int id){
        setUrlData(id + "");
    }
}
