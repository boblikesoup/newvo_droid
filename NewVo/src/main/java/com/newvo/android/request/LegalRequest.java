package com.newvo.android.request;

/**
 * Created by David on 4/6/2014.
 */
public class LegalRequest extends AbstractRequest{

    public LegalRequest() {
        super("api/v1/pages/legal", GET);
    }
}
