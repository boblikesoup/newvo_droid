package com.newvo.android.request;

/**
 * Created by David on 4/6/2014.
 */
public class CreatePostRequest extends AbstractRequest {
    CreatePostRequest() {
        super("/api/v1/posts", GET);
    }
}
