package com.newvo.android.request;

/**
 * Created by David on 4/6/2014.
 */
public class CurrentUserProfileRequest extends AbstractRequest {

    public CurrentUserProfileRequest() {
        super("/api/v1/users", GET);
    }

}
