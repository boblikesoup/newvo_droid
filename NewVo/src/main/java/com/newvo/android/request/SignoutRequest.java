package com.newvo.android.request;

import android.content.Context;

/**
 * Created by David on 4/5/2014.
 */
public class SignoutRequest extends AbstractRequest {


    public SignoutRequest(Context context) {
        super(context, "/signout/mobile", GET);
    }
}
