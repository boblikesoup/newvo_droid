package com.newvo.android.request;

/**
 * Created by David on 4/6/2014.
 */
public class OtherUserProfileRequest extends AbstractRequest {

    public OtherUserProfileRequest(int id) {
        super("api/v1/users/:id", GET);
        setId(id);
    }

    private void setId(int id){
        setUrlData(id + "");
    }
}
