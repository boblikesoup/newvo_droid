package com.newvo.android.request;

/**
 * Created by David on 4/6/2014.
 */
public class UpdateUserDescriptionRequest extends AbstractRequest {

    public UpdateUserDescriptionRequest(String description) {
        super("/api/v1/users/", PATCH);
        setDescription(description);
    }

    private void setDescription(String description){
        setUrlData(description);
    }
}
