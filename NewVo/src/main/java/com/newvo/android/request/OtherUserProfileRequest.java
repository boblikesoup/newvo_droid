package com.newvo.android.request;

/**
 * Created by David on 4/6/2014.
 */
public class OtherUserProfileRequest extends AbstractRequest {

    public OtherUserProfileRequest() {
        super("api/v1/users/:id", GET);
    }

    public void setId(int id){
        setUrlData(id + "");
    }
}
