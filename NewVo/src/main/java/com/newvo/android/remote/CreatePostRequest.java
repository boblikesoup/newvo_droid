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

    public CreatePostRequest(Context context, String caption, ParseFile image1, ParseFile image2) throws MissingCaptionError, MissingImageError {
        if(caption == null || caption.isEmpty()){
            throw new MissingCaptionError();
        } else if(image1 == null){
            throw new MissingImageError();
        }
        post = new Post();
        post.setCaption(caption);
        post.setPhoto1(image1);
        if(image2 != null){
            post.setPhoto2(image2);
        }
        User currentUser = (User) getCurrentUser();
        post.setUser(currentUser);
        post.setStatus(Post.ACTIVE);
        post.setViewableBy(Post.PUBLIC);
        post.setVotes1(0);
        post.setVotes2(0);
        post.setNumberOfSuggestions(0);
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

    public class MissingCaptionError extends Error {
    }

    public class MissingImageError extends Error {
    }
}
