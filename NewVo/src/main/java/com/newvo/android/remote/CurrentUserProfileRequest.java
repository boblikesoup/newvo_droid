package com.newvo.android.remote;

import com.newvo.android.parse.Post;
import com.newvo.android.parse.User;
import com.parse.FindCallback;
import com.parse.ParseQuery;

/**
 * Created by David on 4/26/2014.
 */
public class CurrentUserProfileRequest {

    private ParseQuery<Post> query;

    public CurrentUserProfileRequest(){
        User user = User.getCurrentUser();

        query = ParseQuery.getQuery(Post.class);
        query.whereEqualTo(Post.USER_ID, user);
        query.whereNotEqualTo(Post.STATUS, Post.getStatusValue(Post.DELETED));
        query.orderByDescending("createdAt");
    }

    public void request(String status, FindCallback<Post> callback){
        query.whereEqualTo("status", Post.getStatusValue(status));
        query.findInBackground(callback);
    }


}
