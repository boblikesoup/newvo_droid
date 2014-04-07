package com.newvo.android.request;

/**
 * Created by David on 4/5/2014.
 */
public class LoginRequest extends AbstractRequest {

    public LoginRequest() {
        super("/auth/mobile", GET);
    }
}
