package com.newvo.android.remote;

import android.content.Context;
import com.newvo.android.parse.Post;
import com.newvo.android.parse.User;
import com.parse.ParseACL;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import static com.parse.ParseUser.getCurrentUser;

/**
 * Created by David on 4/27/2014.
 */
public class CreatePostRequest {

    private Post post;

    public CreatePostRequest(Context context, String caption, String image1, String image2) throws MissingCaptionError, MissingImageError {
        if(caption == null || caption.isEmpty()){
            throw new MissingCaptionError();
        } else if(image1 == null){
            throw new MissingImageError();
        }
        post = new Post();
        post.setCaption(caption);
        post.setPhoto1(getParseFile(context, image1));
        if(image2 != null){
            post.setPhoto2(getParseFile(context, image2));
        }
        User currentUser = (User) getCurrentUser();
        post.setUser(currentUser);
        post.setStatus(Post.ACTIVE);
        post.setViewableBy(Post.PUBLIC);
        post.setVotes1(0);
        post.setVotes2(0);
        post.setNumberOfSuggestions(0);

        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setWriteAccess(currentUser, true);
        acl.setReadAccess(currentUser, true);
        post.setACL(acl);
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

    public class MissingCaptionError extends Error {
    }

    public class MissingImageError extends Error {
    }
}
