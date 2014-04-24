package com.newvo.android.request;

import android.content.Context;

/**
 * Created by David on 4/5/2014.
 */
public class LoginRequest extends AbstractRequest {

    public LoginRequest(Context context) {
        super(context, "/auth/mobile", GET);
    }
}
