package com.newvo.android.remote;

import com.newvo.android.parse.Post;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;

import java.util.HashMap;

/**
 * Created by David on 6/27/2014.
 */
public class ClearCountersRequest {

    private final HashMap<String, Object> params;
    private final String functionName;

    public ClearCountersRequest(Post post){
        params = new HashMap<String, Object>();
        params.put("postId", post.getObjectId());
        functionName = "clearPost";
    }

    public void request(FunctionCallback<Object> callback){
        ParseCloud.callFunctionInBackground(functionName, params, callback);
    }
}
