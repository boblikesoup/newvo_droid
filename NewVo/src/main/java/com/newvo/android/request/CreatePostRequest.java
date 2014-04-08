package com.newvo.android.request;

import android.util.Log;

/**
 * Created by David on 4/6/2014.
 */
public class CreatePostRequest extends PostRequest {

    public static final int PUBLIC = 0;
    public static final int FRIENDS = 1;
    public static final int GROUP = 2;

    CreatePostRequest() {
        super("/api/v1/posts");
    }

    public void setDescription(String description){
        addUrlParam("description", description);
    }

    public void setViewableBy(int viewableBy){
        if(viewableBy < 0 || viewableBy > 2){
            Log.e("JSON", viewableBy + " is not within range 0-2.");
            return;
        }
        addBodyParam("viewable_by", viewableBy + "");

    }
}
