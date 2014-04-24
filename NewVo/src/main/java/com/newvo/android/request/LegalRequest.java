package com.newvo.android.request;

import android.content.Context;

/**
 * Created by David on 4/6/2014.
 */
public class LegalRequest extends AbstractRequest {

    public LegalRequest(Context context) {
        super(context, "api/v1/pages/legal", GET);
    }
}
