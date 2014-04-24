package com.newvo.android.request;

import android.content.Context;
import com.koushikdutta.ion.builder.Builders;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by David on 4/7/2014.
 */
abstract class PostRequest extends AbstractRequest {

    final static String POST = "POST";

    private Map<String, String> bodyParams = new HashMap<String, String>();

    PostRequest(Context context, String requestType) {
        super(context, POST, requestType);
    }

    void addBodyParam(String name, String value) {
        bodyParams.put(name, value);
    }

    @Override
    void addMiscData(Builders.Any.B load) {
        //Add body params
        for (Map.Entry<String, String> param : bodyParams.entrySet()) {
            load.setBodyParameter(param.getKey(), param.getValue());
        }
    }
}
