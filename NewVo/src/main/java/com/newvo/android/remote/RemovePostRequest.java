package com.newvo.android.remote;

import com.newvo.android.parse.Post;
import com.parse.DeleteCallback;

/**
 * Created by David on 4/29/2014.
 */
public class RemovePostRequest {

    private Post post;

    public RemovePostRequest(Post post){
        this.post = post;
    }

    public void request(DeleteCallback callback){
        post.deleteInBackground(callback);
    }
}
