package com.newvo.android.request;

import android.content.Context;

/**
 * Created by David on 4/6/2014.
 */
public class UpdateUserDescriptionRequest extends AbstractRequest {

    public UpdateUserDescriptionRequest(Context context, String description) {
        super(context, "/api/v1/users/", PATCH);
        setDescription(description);
    }

    private void setDescription(String description) {
        setUrlData(description);
    }
}
