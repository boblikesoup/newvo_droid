package com.newvo.android.remote;

import android.content.Context;
import com.newvo.android.parse.Post;
import com.newvo.android.parse.User;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import static com.parse.ParseUser.getCurrentUser;

/**
 * Created by David on 4/27/2014.
 */
public class CreatePostRequest {

    private Post post;

    public CreatePostRequest(Context context, String caption, String image1, String image2){
        if(image1 == null){
            return;
        }
        post = new Post();
        if(caption != null) {
            post.setCaption(caption);
        }
        post.setPhoto1(getParseFile(context, image1));
        if(image2 != null){
            post.setPhoto2(getParseFile(context, image2));
        }
        post.setUser((User) getCurrentUser());
    }

    private ParseFile getParseFile(Context context, String image1) {
        return Post.createParseFile(context.getContentResolver(), image1);
    }

    public void request(){
        request(null);
    }

    public void request(SaveCallback saveCallback){
        if(saveCallback == null){
            post.saveEventually();
        } else {
            post.saveEventually(saveCallback);
        }
    }
}
