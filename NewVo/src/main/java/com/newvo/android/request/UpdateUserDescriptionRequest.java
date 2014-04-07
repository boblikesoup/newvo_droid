package com.newvo.android.request;

/**
 * Created by David on 4/6/2014.
 */
public class UpdateUserDescriptionRequest extends AbstractRequest {

    public UpdateUserDescriptionRequest() {
        super("/api/v1/users/describe", PATCH);
    }

    public void setDescription(String description){
        setUrlData(description);
    }
}
