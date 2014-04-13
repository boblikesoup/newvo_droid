package com.newvo.android.request;

import com.koushikdutta.async.future.FutureCallback;
import com.newvo.android.json.CurrentUserProfile;

/**
 * Created by David on 4/6/2014.
 */
public class CurrentUserProfileRequest extends AbstractRequest {

    public CurrentUserProfileRequest() {
        super("/api/v1/users", GET);
    }

    public void request(FutureCallback<CurrentUserProfile> callback) {
        super.makeRequest(CurrentUserProfile.class, callback);
    }
}
