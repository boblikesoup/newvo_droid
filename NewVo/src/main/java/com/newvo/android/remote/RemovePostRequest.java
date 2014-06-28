package com.newvo.android.remote;

import com.newvo.android.parse.Post;
import com.parse.SaveCallback;

/**
 * Created by David on 4/29/2014.
 */
public class RemovePostRequest {

    private Post post;

    public RemovePostRequest(Post post){
        this.post = post;
        this.post.setStatus(Post.DELETED);
    }

    public void request(SaveCallback callback){
        post.saveInBackground(callback);
    }
}
