package com.newvo.android.remote;

import com.newvo.android.parse.Post;
import com.parse.SaveCallback;

/**
 * Created by David on 5/4/2014.
 */
public class SetPostActiveRequest {

    private final Post post;

    public SetPostActiveRequest(Post item, String status) {
        this.post = item;
       item.setStatus(status);
    }

    public void request(){
        request(null);
    }

    public void request(SaveCallback saveCallback){
        if(saveCallback == null){
            post.saveInBackground();
        } else {
            post.saveInBackground(saveCallback);
        }
    }
}
