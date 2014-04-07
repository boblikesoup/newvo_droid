package com.newvo.android.request;

/**
 * Created by David on 4/5/2014.
 */
public class SignoutRequest extends AbstractRequest {


    public SignoutRequest() {
        super("/signout/mobile", GET);
    }
}
